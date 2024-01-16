package com.lzb.www.dao;

import com.lzb.www.pojo.po.Movie;

import java.util.List;

public interface MovieDao {
    boolean insertMovie(Movie movie);

    List<Object> listMovies(int begin, int size);

    Number countMovies();

    boolean updatePoster(String posterURL, int id);

    boolean updateMovie(Movie movie);

    boolean deleteMovie(int id);

    Movie getMovie(int id);

    List<Object> listMoviesByParam(int begin, int size, String param);

    Number countMoviesByParam(String param);

    List<Object> listMoviesWithSort(int begin, int size);

}
