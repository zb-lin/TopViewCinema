package com.lzb.www.controller.mock;

import com.lzb.www.api.MovieService;
import com.lzb.www.pojo.po.Movie;
import com.lzb.www.proto.RPCProto;
import sspring.bean.annotation.Hosted;

import java.time.LocalDateTime;

@Hosted
public class MockMovieServiceImpl implements MovieService {

    @Override
    public byte[] insertMovie(byte[] bytes) {
        RPCProto.MovieResponse.InsertMovieResult insertMovieResult = RPCProto.MovieResponse.InsertMovieResult.newBuilder()
                .setResult(true).build();
        return RPCProto.MovieResponse.newBuilder().setInsertMovieResult(insertMovieResult).build().toByteArray();
    }

    @Override
    public byte[] listMovies(byte[] bytes) {
        RPCProto.MoviePageBean.Builder moviePageBean = getMoviePageBean();
        RPCProto.MovieResponse.ListMoviesResult listMoviesResult = RPCProto.MovieResponse.ListMoviesResult.newBuilder()
                .setMoviePageBean(moviePageBean).build();
        return RPCProto.MovieResponse.newBuilder().setListMoviesResult(listMoviesResult).build().toByteArray();

    }

    private RPCProto.MoviePageBean.Builder getMoviePageBean() {
        RPCProto.MoviePageBean.Builder moviePageBean = RPCProto.MoviePageBean.newBuilder();
        Movie movie = new Movie(1, "1", "1", "1", "1", "1", 1,
                "http://localhost:8080/TopViewCinema/upload/2023-04-23-05-46-05img.png", LocalDateTime.now(), 1, 1, 1);
        RPCProto.Movie movie1 = RPCProto.Movie.newBuilder()
                .setId(movie.getId())
                .setName(movie.getName())
                .setDirector(movie.getDirector())
                .setLeadingRole(movie.getLeadingRole())
                .setType(movie.getType())
                .setProductionCountry(movie.getProductionCountry())
                .setTheTimeOfMovie(movie.getTheTimeOfMovie())
                .setPoster(movie.getPoster())
                .setReleaseTime(movie.getReleaseTime().toString())
                .setPrice(movie.getPrice())
                .setEvaluate(movie.getEvaluate())
                .setEvaluateCount(movie.getEvaluateCount()).build();
        moviePageBean.addMovie(movie1);
        moviePageBean.setTotalCount(1);
        return moviePageBean;
    }


    @Override
    public byte[] uploadPoster(byte[] bytes) {
        RPCProto.MovieResponse.UploadPosterResult uploadPosterResult = RPCProto.MovieResponse.UploadPosterResult.newBuilder()
                .setResult(true).build();
        return RPCProto.MovieResponse.newBuilder().setUploadPosterResult(uploadPosterResult).build().toByteArray();
    }

    @Override
    public byte[] updateMovie(byte[] bytes) {
        RPCProto.MovieResponse.UpdateMovieResult updateMovieResult = RPCProto.MovieResponse.UpdateMovieResult.newBuilder()
                .setResult(true).build();
        return RPCProto.MovieResponse.newBuilder().setUpdateMovieResult(updateMovieResult).build().toByteArray();
    }

    private Movie getMovie(RPCProto.Movie movie) {
        return new Movie(movie.getId(), movie.getName(), movie.getDirector(), movie.getLeadingRole(), movie.getType(),
                movie.getProductionCountry(), movie.getTheTimeOfMovie(), movie.getPoster(),
                LocalDateTime.parse(movie.getReleaseTime()), movie.getPrice(), movie.getEvaluate(), movie.getEvaluateCount());
    }

    @Override
    public byte[] deleteMovie(byte[] bytes) {
        RPCProto.MovieResponse.DeleteMovieResult deleteMovieResult = RPCProto.MovieResponse.DeleteMovieResult.newBuilder()
                .setResult(true).build();
        return RPCProto.MovieResponse.newBuilder().setDeleteMovieResult(deleteMovieResult).build().toByteArray();
    }

    @Override
    public byte[] listMoviesByParam(byte[] bytes) {
        RPCProto.MoviePageBean.Builder moviePageBean = getMoviePageBean();
        RPCProto.MovieResponse.ListMoviesByParamResult listMoviesByParamResult = RPCProto.MovieResponse.ListMoviesByParamResult.newBuilder()
                .setMoviePageBean(moviePageBean).build();
        return RPCProto.MovieResponse.newBuilder().setListMoviesByParamResult(listMoviesByParamResult).build().toByteArray();
    }

    @Override
    public byte[] updateEvaluate(byte[] bytes) {
        RPCProto.MovieResponse.UpdateEvaluateResult updateEvaluateResult = RPCProto.MovieResponse.UpdateEvaluateResult.newBuilder()
                .setResult(true).build();
        return RPCProto.MovieResponse.newBuilder().setUpdateEvaluateResult(updateEvaluateResult).build().toByteArray();
    }

}
