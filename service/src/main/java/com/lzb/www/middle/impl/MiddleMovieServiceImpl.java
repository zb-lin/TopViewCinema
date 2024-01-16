package com.lzb.www.middle.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ServiceException;
import com.lzb.www.middle.MiddleMovieService;
import com.lzb.www.pojo.po.Movie;
import com.lzb.www.pojo.vo.EvaluateDate;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.service.MovieService;
import sspring.bean.annotation.Hosted;
import sspring.bean.annotation.LoggerProxy;

import java.time.LocalDateTime;

@Hosted
public class MiddleMovieServiceImpl implements MiddleMovieService {
    @LoggerProxy
    private MovieService movieService;

    @Override
    public byte[] insertMovie(byte[] bytes) throws InvalidProtocolBufferException, ServiceException {
        RPCProto.MovieRequest movieRequest = RPCProto.MovieRequest.parseFrom(bytes);
        RPCProto.MovieRequest.InsertMovieInfo insertMovieInfo = movieRequest.getInsertMovieInfo();
        RPCProto.Movie movie = insertMovieInfo.getMovie();
        Movie movie1 = getMovie(movie);
        if (!checkUpMovie(movie1)) {
            throw new ServiceException("movie has a null field");
        }
        boolean result = movieService.insertMovie(movie1);
        RPCProto.MovieResponse.InsertMovieResult insertMovieResult = RPCProto.MovieResponse.InsertMovieResult.newBuilder()
                .setResult(result).build();
        return RPCProto.MovieResponse.newBuilder().setInsertMovieResult(insertMovieResult).build().toByteArray();
    }

    private boolean checkUpMovie(Movie movie) {
        return movie.getId() != 0 && !"".equals(movie.getName()) &&
                !"".equals(movie.getDirector()) && !"".equals(movie.getLeadingRole()) && !"".equals(movie.getType()) &&
                !"".equals(movie.getProductionCountry()) && movie.getTheTimeOfMovie() != 0 && !"".equals(movie.getPoster()) &&
                movie.getReleaseTime() != null && movie.getPrice() != 0;
    }

    @Override
    public byte[] listMovies(byte[] bytes) throws InvalidProtocolBufferException, ServiceException {
        RPCProto.MovieRequest movieRequest = RPCProto.MovieRequest.parseFrom(bytes);
        RPCProto.MovieRequest.ListMoviesInfo listMoviesInfo = movieRequest.getListMoviesInfo();
        int currentPage = listMoviesInfo.getCurrentPage();
        int pageSize = listMoviesInfo.getPageSize();
        int status = listMoviesInfo.getStatus();
        if (currentPage == 0 || pageSize == 0) {
            throw new ServiceException("listMovies: currentPage pageSize not exist");
        }
        PageBean<Object> pageBean = movieService.listMovies(currentPage, pageSize, status);
        RPCProto.MoviePageBean.Builder moviePageBean = getMoviePageBean(pageBean);

        RPCProto.MovieResponse.ListMoviesResult listMoviesResult = RPCProto.MovieResponse.ListMoviesResult.newBuilder()
                .setMoviePageBean(moviePageBean).build();
        return RPCProto.MovieResponse.newBuilder().setListMoviesResult(listMoviesResult).build().toByteArray();

    }

    private RPCProto.MoviePageBean.Builder getMoviePageBean(PageBean<Object> pageBean) {
        RPCProto.MoviePageBean.Builder moviePageBean = RPCProto.MoviePageBean.newBuilder();
        for (Object row : pageBean.getRows()) {
            Movie movie = (Movie) row;
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
        }
        moviePageBean.setTotalCount(Integer.parseInt(String.valueOf(pageBean.getTotalCount())));
        return moviePageBean;
    }


    @Override
    public byte[] uploadPoster(byte[] bytes) throws InvalidProtocolBufferException, ServiceException {
        RPCProto.MovieRequest movieRequest = RPCProto.MovieRequest.parseFrom(bytes);
        RPCProto.MovieRequest.UploadPosterInfo uploadPosterInfo = movieRequest.getUploadPosterInfo();
        int id = uploadPosterInfo.getId();
        String fileName = uploadPosterInfo.getFileName();
        if (id == 0 || "".equals(fileName)) {
            throw new ServiceException("uploadPoster: id fileName not exist");
        }
        boolean result = movieService.uploadPoster(fileName, id);
        RPCProto.MovieResponse.UploadPosterResult uploadPosterResult = RPCProto.MovieResponse.UploadPosterResult.newBuilder()
                .setResult(result).build();
        return RPCProto.MovieResponse.newBuilder().setUploadPosterResult(uploadPosterResult).build().toByteArray();
    }

    @Override
    public byte[] updateMovie(byte[] bytes) throws InvalidProtocolBufferException, ServiceException {
        RPCProto.MovieRequest movieRequest = RPCProto.MovieRequest.parseFrom(bytes);
        RPCProto.MovieRequest.UpdateMovieInfo updateMovieInfo = movieRequest.getUpdateMovieInfo();
        RPCProto.Movie movie = updateMovieInfo.getMovie();
        Movie movie1 = getMovie(movie);
        if (!checkUpMovie(movie1)) {
            throw new ServiceException("movie has a null field");
        }
        boolean result = movieService.updateMovie(movie1);
        RPCProto.MovieResponse.UpdateMovieResult updateMovieResult = RPCProto.MovieResponse.UpdateMovieResult.newBuilder()
                .setResult(result).build();
        return RPCProto.MovieResponse.newBuilder().setUpdateMovieResult(updateMovieResult).build().toByteArray();
    }

    private Movie getMovie(RPCProto.Movie movie) {
        return new Movie(movie.getId(), movie.getName(), movie.getDirector(), movie.getLeadingRole(), movie.getType(),
                movie.getProductionCountry(), movie.getTheTimeOfMovie(), movie.getPoster(),
                LocalDateTime.parse(movie.getReleaseTime()), movie.getPrice(), movie.getEvaluate(), movie.getEvaluateCount());
    }

    @Override
    public byte[] deleteMovie(byte[] bytes) throws InvalidProtocolBufferException, ServiceException {
        RPCProto.MovieRequest movieRequest = RPCProto.MovieRequest.parseFrom(bytes);
        RPCProto.MovieRequest.DeleteMovieInfo deleteMovieInfo = movieRequest.getDeleteMovieInfo();
        int id = deleteMovieInfo.getId();
        if (id == 0) {
            throw new ServiceException("deleteMovie: id not exist");
        }
        boolean result = movieService.deleteMovie(id);

        RPCProto.MovieResponse.DeleteMovieResult deleteMovieResult = RPCProto.MovieResponse.DeleteMovieResult.newBuilder()
                .setResult(result).build();
        return RPCProto.MovieResponse.newBuilder().setDeleteMovieResult(deleteMovieResult).build().toByteArray();
    }

    @Override
    public byte[] listMoviesByParam(byte[] bytes) throws InvalidProtocolBufferException, ServiceException {
        RPCProto.MovieRequest movieRequest = RPCProto.MovieRequest.parseFrom(bytes);
        RPCProto.MovieRequest.ListMoviesByParamInfo listMoviesByParamInfo = movieRequest.getListMoviesByParamInfo();
        int currentPage = listMoviesByParamInfo.getCurrentPage();
        int pageSize = listMoviesByParamInfo.getPageSize();
        String param = listMoviesByParamInfo.getParam();
        if (currentPage == 0 || pageSize == 0) {
            throw new ServiceException("listMoviesByParam: currentPage pageSize not exist");
        }
        PageBean<Object> pageBean = movieService.listMoviesByParam(currentPage, pageSize, param);

        RPCProto.MoviePageBean.Builder moviePageBean = getMoviePageBean(pageBean);

        RPCProto.MovieResponse.ListMoviesByParamResult listMoviesByParamResult = RPCProto.MovieResponse.ListMoviesByParamResult.newBuilder()
                .setMoviePageBean(moviePageBean).build();
        return RPCProto.MovieResponse.newBuilder().setListMoviesByParamResult(listMoviesByParamResult).build().toByteArray();
    }

    @Override
    public byte[] updateEvaluate(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.MovieRequest movieRequest = RPCProto.MovieRequest.parseFrom(bytes);
        RPCProto.MovieRequest.UpdateEvaluateInfo updateEvaluateInfo = movieRequest.getUpdateEvaluateInfo();
        RPCProto.EvaluateDate evaluateDate = updateEvaluateInfo.getEvaluateDate();

        EvaluateDate evaluateDate1 = new EvaluateDate(evaluateDate.getMovieId(), evaluateDate.getUserId(),
                evaluateDate.getEvaluate(), evaluateDate.getComment());
        boolean result = movieService.updateEvaluate(evaluateDate1);
        RPCProto.MovieResponse.UpdateEvaluateResult updateEvaluateResult = RPCProto.MovieResponse.UpdateEvaluateResult.newBuilder()
                .setResult(result).build();
        return RPCProto.MovieResponse.newBuilder().setUpdateEvaluateResult(updateEvaluateResult).build().toByteArray();
    }

}
