package com.lzb.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.lzb.www.dao.CommentDao;
import com.lzb.www.dao.MovieDao;
import com.lzb.www.exception.ServiceException;
import com.lzb.www.pojo.po.Comment;
import com.lzb.www.pojo.po.Movie;
import com.lzb.www.pojo.vo.EvaluateDate;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.service.MovieService;
import com.lzb.www.util.StringUtils;
import redis.clients.jedis.Jedis;
import rpc.util.JedisUtil;
import sspring.bean.annotation.Autowired;
import sspring.bean.annotation.Hosted;

import java.util.*;

import static com.lzb.www.constant.GlobalConstant.*;

@Hosted
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private CommentDao commentDao;


    @Override
    public boolean insertMovie(Movie movie) {
        if (isEmpty(movie)) {
            return false;
        }
        movie.setPoster(DEFAULT_HEAD);
        return movieDao.insertMovie(movie);
    }

    @Override
    public PageBean<Object> listMovies(int currentPage, int pageSize, int status) {
        int begin = (currentPage - INT_ONE) * pageSize;
        List<Object> rows = new ArrayList<>();
        boolean flag = false;

        Jedis jedis = JedisUtil.getJedis();
        // 先从缓存中取
        for (int i = 0; i < pageSize; i++) {
            // key: 数据库第几个
            int index = begin + i;
            String movieJson = jedis.hget(REDIS_MOVIE_KEY, String.valueOf(index));
            Movie movie = JSON.parseObject(movieJson, Movie.class);
            // 有一个为空则查询数据库
            if (movie == null) {
                flag = true;
                break;
            }
            rows.add(movie);
        }

        if (status == COMMON_QUERY && flag) {
            // 普通查询
            rows = movieDao.listMovies(begin, pageSize);
        } else if (status == PAGE_QUERY && flag) {
            // 条件查询(排序)
            rows = movieDao.listMoviesWithSort(begin, pageSize);
        }
        // 缓存空对象, 解决缓存穿透问题
        if (rows == null || rows.size() == 0) {
            for (int i = 0; i < pageSize; i++) {
                // key: 数据库第几个
                int index = begin + i;
                jedis.hset(REDIS_MOVIE_KEY, String.valueOf(index), "");
                jedis.expire(REDIS_MOVIE_DELETE_KEY + index, REDIS_TIMEOUT);
            }
            flag = false;
        }
        if (flag) {
            for (int i = 0; i < rows.size(); i++) {
                int index = begin + i;
                Movie movie = (Movie) rows.get(i);
                int movieId = movie.getId();
                String jsonString = JSON.toJSONString(movie);

                // key: 数据库第几个   randomTimeout  1-50 的随机数  缓存雪崩
                int randomTimeout = (int) (Math.random() * 50);
                // 缓存每天电影数据
                jedis.hset(REDIS_MOVIE_KEY, String.valueOf(index), jsonString);
                jedis.expire(REDIS_MOVIE_DELETE_KEY + index, REDIS_TIMEOUT + randomTimeout);
                // key movie id  value: 数据库第几个
                jedis.hset(REDIS_MOVIE_ID_KEY, String.valueOf(movieId), String.valueOf(index));
                jedis.expire(REDIS_MOVIE_ID_DELETE_KEY + movieId, REDIS_TIMEOUT + randomTimeout);
            }
        }
        JedisUtil.closeJedis(jedis);

        Number count = movieDao.countMovies();
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    @Override
    public boolean uploadPoster(String fileName, int id) {
        Jedis jedis = null;
        try {
            String posterURL = UPLOAD_IMG_PATH + fileName;
            if (!movieDao.updatePoster(posterURL, id)) {
                return false;
            }
            jedis = JedisUtil.getJedis();
            // 通过movie id 获取 movie 位于数据库第几个
            String index = jedis.hget(REDIS_MOVIE_ID_KEY, String.valueOf(id));
            if (index != null) {
                // 获取movie
                String movieJson = jedis.hget(REDIS_MOVIE_KEY, index);
                if (movieJson == null) {
                    jedis.hdel(REDIS_MOVIE_ID_KEY, String.valueOf(id));
                    return movieDao.updatePoster(posterURL, id);
                }
                // 更新缓存
                Movie movie = JSON.parseObject(movieJson, Movie.class);
                movie.setPoster(posterURL);
                String jsonString = JSON.toJSONString(movie);
                jedis.hset(REDIS_MOVIE_KEY, index, jsonString);
            }
            return true;
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    @Override
    public boolean updateMovie(Movie movie) {
        Jedis jedis = null;
        try {
            if (isEmpty(movie)) {
                return false;
            }
            if (!movieDao.updateMovie(movie)) {
                return false;
            }
            jedis = JedisUtil.getJedis();
            String index = jedis.hget(REDIS_MOVIE_ID_KEY, String.valueOf(movie.getId()));
            if (index == null) {
                return movieDao.updateMovie(movie);
            }
            String movieJson = jedis.hget(REDIS_MOVIE_KEY, index);
            if (movieJson == null) {
                jedis.hdel(REDIS_MOVIE_ID_KEY, String.valueOf(movie.getId()));
                return movieDao.updateMovie(movie);
            }
            String jsonString = JSON.toJSONString(movie);
            jedis.hset(REDIS_MOVIE_KEY, index, jsonString);
            return true;
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    @Override
    public boolean deleteMovie(int id) {
        // 删除电影, 排位可能发生变化, 将缓存删除
        Jedis jedis = null;
        try {
            if (!movieDao.deleteMovie(id)) {
                return false;
            }
            jedis = JedisUtil.getJedis();
            jedis.del(REDIS_MOVIE_ID_KEY, REDIS_MOVIE_KEY);
            return true;
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    @Override
    public PageBean<Object> listMoviesByParam(int currentPage, int pageSize, String param) {
        if (!StringUtils.isNotEmpty(param)) {
            return listMovies(currentPage, pageSize, COMMON_QUERY);
        }
        int begin = (currentPage - INT_ONE) * pageSize;
        List<Object> rows = movieDao.listMoviesByParam(begin, pageSize, param);
        Number count = movieDao.countMoviesByParam(param);
        PageBean<Object> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    @Override
    public boolean updateEvaluate(EvaluateDate evaluateDate) {
        Jedis jedis = null;
        try {
            Movie movie = movieDao.getMovie(evaluateDate.getMovieId());
            Integer evaluate1 = movie.getEvaluate();
            Integer evaluateCount = movie.getEvaluateCount();
            // 评分和评分人数都为0, 直接set评分
            if (evaluate1 == INT_ZERO && evaluateCount == INT_ZERO) {
                movie.setEvaluate(evaluateDate.getEvaluate() * INT_TEN);
            } else {
                double evaluate2 = (evaluate1 / DOUBLE_TEN * evaluateCount + evaluateDate.getEvaluate()) / (evaluateCount + INT_ONE);
                String format = String.format(KEEP_ONE_DIGIT_FORMAT, evaluate2);
                movie.setEvaluate((int) (Double.parseDouble(format) * INT_TEN));
            }
            movie.setEvaluateCount(evaluateCount + INT_ZERO);

            boolean result = movieDao.updateMovie(movie) && commentDao.insertComment(new Comment(null, evaluateDate.getMovieId(),
                    evaluateDate.getUserId(), evaluateDate.getEvaluate(), evaluateDate.getComment()));
            if (!result) {
                return false;
            }

            jedis = JedisUtil.getJedis();
            String index = jedis.hget(REDIS_MOVIE_ID_KEY, String.valueOf(movie.getId()));
            boolean flag = index != null;
            if (flag) {
                String movieJson = jedis.hget(REDIS_MOVIE_KEY, index);
                if (movieJson == null) {
                    jedis.hdel(REDIS_MOVIE_ID_KEY, String.valueOf(movie.getId()));
                    flag = false;
                }
            }
            if (flag) {
                String jsonString = JSON.toJSONString(movie);
                jedis.hset(REDIS_MOVIE_KEY, index, jsonString);
            }
            return true;
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            JedisUtil.closeJedis(jedis);
        }
    }

    private boolean isEmpty(Movie movie) {
        return !(StringUtils.isNotEmpty(movie.getName())
                && StringUtils.isNotEmpty(movie.getDirector())
                && StringUtils.isNotEmpty(movie.getLeadingRole())
                && StringUtils.isNotEmpty(movie.getType())
                && StringUtils.isNotEmpty(movie.getProductionCountry())
                && movie.getTheTimeOfMovie() != null
                && movie.getReleaseTime() != null
                && movie.getPrice() != null);
    }
}
