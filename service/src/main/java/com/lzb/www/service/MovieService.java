package com.lzb.www.service;

import com.lzb.www.pojo.po.Movie;
import com.lzb.www.pojo.vo.EvaluateDate;
import com.lzb.www.pojo.vo.PageBean;

public interface MovieService {
    /**
     * 添加电影
     *
     * @param movie 电影
     * @return 是否添加成功
     */
    boolean insertMovie(Movie movie);

    /**
     * 分页查询电影
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param status      是否是条件查询 0 普通查询 1条件查询
     * @return 电影集合
     */
    PageBean<Object> listMovies(int currentPage, int pageSize, int status);

    /**
     * 上传海报
     *
     * @param fileName 图片路径
     * @param id       电影id
     * @return 是否更新成功
     */
    boolean uploadPoster(String fileName, int id);

    /**
     * 更新电影
     *
     * @param movie 要更新的电影
     * @return 是否更新成功
     */
    boolean updateMovie(Movie movie);

    /**
     * 删除电影
     *
     * @param id 电影id
     * @return 是否删除成功
     */
    boolean deleteMovie(int id);

    /**
     * 条件查询
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @param param       条件
     * @return 电影集合
     */
    PageBean<Object> listMoviesByParam(int currentPage, int pageSize, String param);

    /**
     * 更新评分
     *
     * @param evaluateDate 评分数据
     * @return 是否更新成功
     */
    boolean updateEvaluate(EvaluateDate evaluateDate);

}
