package com.lzb.www.dao.impl;

import com.lzb.www.dao.MovieDao;
import com.lzb.www.pojo.po.Movie;
import sspring.bean.annotation.Hosted;

import java.util.List;
import java.util.Optional;

@Hosted
public class MovieDaoImpl extends BaseDao implements MovieDao {
    @Override
    public boolean insertMovie(Movie movie) {
        return query.insert(movie);
    }

    @Override
    public List<Object> listMovies(int begin, int size) {
        String sql = "select * from tb_movie limit ?,?";
        return query.queryRows(sql, Movie.class, new Object[]{begin, size});
    }

    @Override
    public Number countMovies() {
        String sql = "select count(*) from tb_movie";
        return query.queryNumber(sql, null);
    }

    @Override
    public boolean updatePoster(String posterURL, int id) {
        String sql = "update tb_movie set poster = ? where id = ?";
        return query.update(sql, new Object[]{posterURL, String.valueOf(id)});
    }

    @Override
    public boolean updateMovie(Movie movie) {
        return query.update(movie, new String[]{
                "name", "director", "leadingRole", "type", "productionCountry",
                "theTimeOfMovie", "releaseTime", "price", "evaluate", "evaluateCount"});
    }

    @Override
    public boolean deleteMovie(int id) {
        return query.delete(Movie.class, id);
    }

    @Override
    public Movie getMovie(int id) {
        String sql = "select * from tb_movie where id = ?";
        List<Object> movies = query.queryRows(sql, Movie.class, new Object[]{id});
        return Optional.ofNullable(movies).isPresent() ? (Movie) movies.get(0) : null;
    }

    @Override
    public List<Object> listMoviesByParam(int begin, int size, String param) {
        param = "%" + param + "%";
        String sql = "select * from (select * from tb_movie where name like ? or type like ? or release_time like ?) tb limit ?,?";
        return query.queryRows(sql, Movie.class, new Object[]{param, param, param, begin, size});
    }

    @Override
    public Number countMoviesByParam(String param) {
        param = "%" + param + "%";
        String sql = "select count(*) from tb_movie where name like ?  or type like ? or release_time like ?";
        return query.queryNumber(sql, new Object[]{param, param, param});
    }

    @Override
    public List<Object> listMoviesWithSort(int begin, int size) {
        String sql = "select * from tb_movie order by evaluate desc limit ?,? ";
        return query.queryRows(sql, Movie.class, new Object[]{begin, size});
    }


}
