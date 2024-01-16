package com.lzb.www.api;

public interface MovieService {

    byte[] insertMovie(byte[] bytes);

    byte[] listMovies(byte[] bytes);

    byte[] uploadPoster(byte[] bytes);

    byte[] updateMovie(byte[] bytes);

    byte[] deleteMovie(byte[] bytes);

    byte[] listMoviesByParam(byte[] bytes);

    byte[] updateEvaluate(byte[] bytes);
}
