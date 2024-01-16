package com.lzb.www.controller;


import com.lzb.www.annotation.File;
import com.lzb.www.annotation.Param;
import com.lzb.www.api.MovieService;
import com.lzb.www.pojo.vo.EvaluateDate;
import com.lzb.www.pojo.po.Movie;
import com.lzb.www.pojo.vo.PageBean;
import com.lzb.www.pojo.vo.Response;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.util.ProtoBufUtil;
import sspring.bean.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.lzb.www.constant.WebConstant.*;

@Hosted
@WebServlet("/movie/*")
@MultipartConfig(location = UPLOAD_IMG_FOLDER_URL, maxFileSize = MAX_FILE_SIZE)
public class MovieServlet extends BaseServlet {
    //    @MockBean
    @RpcProxy
    private MovieService movieService;

    public Response<?> uploadMovie(@Param Movie movie) throws IOException {
        RPCProto.MovieRequest movieRequest = ProtoBufUtil.uploadMovieRequest(movie);
        byte[] bytes = movieService.insertMovie(movieRequest.toByteArray());
        RPCProto.MovieResponse movieResponse = RPCProto.MovieResponse.parseFrom(bytes);
        return Response.response(SUMMIT_SUCCESS, SUMMIT_ERROR, movieResponse.getInsertMovieResult().getResult());
    }

    public Response<?> listMovies(@Param(CURRENT_PAGE) Integer currentPage, @Param(PAGE_SIZE) Integer pageSize,
                                  @Param(STATUS) Integer status) throws IOException {
        RPCProto.MovieRequest movieRequest = ProtoBufUtil.listMoviesRequest(currentPage, pageSize, status);
        byte[] bytes = movieService.listMovies(movieRequest.toByteArray());
        PageBean<Movie> pageBean = ProtoBufUtil.listMoviesResponse(bytes, COMMON_QUERY);
        return Response.response(pageBean, QUERY_SUCCESS, QUERY_ERROR, bytes != null);
    }

    public Response<?> uploadPoster(@File Part file, @Param(ID) Integer id) throws IOException, ServletException {
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)) + file.getSubmittedFileName();
        file.write(fileName);

        RPCProto.MovieRequest movieRequest = ProtoBufUtil.uploadPosterRequest(id, fileName);
        byte[] bytes = movieService.uploadPoster(movieRequest.toByteArray());
        RPCProto.MovieResponse movieResponse = RPCProto.MovieResponse.parseFrom(bytes);
        return Response.response(SUMMIT_SUCCESS, SUMMIT_ERROR, movieResponse.getUploadPosterResult().getResult());
    }

    public Response<?> updateMovie(@Param Movie movie) throws IOException {
        RPCProto.MovieRequest movieRequest = ProtoBufUtil.updateMovieRequest(movie);
        byte[] bytes = movieService.updateMovie(movieRequest.toByteArray());
        RPCProto.MovieResponse movieResponse = RPCProto.MovieResponse.parseFrom(bytes);
        return Response.response(UPDATE_SUCCESS, UPDATE_ERROR, movieResponse.getUpdateMovieResult().getResult());
    }

    public Response<?> deleteMovie(@Param(ID) Integer id) throws IOException {
        RPCProto.MovieRequest movieRequest = ProtoBufUtil.deleteMovieRequest(id);
        byte[] bytes = movieService.deleteMovie(movieRequest.toByteArray());
        RPCProto.MovieResponse movieResponse = RPCProto.MovieResponse.parseFrom(bytes);
        return Response.response(DELETE_SUCCESS, DELETE_ERROR, movieResponse.getDeleteMovieResult().getResult());
    }

    public Response<?> listMoviesByParam(@Param(CURRENT_PAGE) Integer currentPage, @Param(PAGE_SIZE) Integer pageSize,
                                         @Param(PARAM) String param) throws IOException {
        RPCProto.MovieRequest movieRequest = ProtoBufUtil.listMoviesByParamRequest(currentPage, pageSize, param);
        byte[] bytes = movieService.listMoviesByParam(movieRequest.toByteArray());
        PageBean<Movie> pageBean = ProtoBufUtil.listMoviesResponse(bytes, PAGINATION_QUERY);
        return Response.response(pageBean, QUERY_SUCCESS, QUERY_ERROR, bytes != null);
    }

    public Response<?> updateEvaluate(@Param EvaluateDate evaluateDate) throws IOException {
        RPCProto.MovieRequest movieRequest = ProtoBufUtil.updateEvaluateRequest(evaluateDate);
        byte[] bytes = movieService.updateEvaluate(movieRequest.toByteArray());
        RPCProto.MovieResponse movieResponse = RPCProto.MovieResponse.parseFrom(bytes);
        return Response.response(SUMMIT_SUCCESS, SUMMIT_SUCCESS, movieResponse.getUpdateEvaluateResult().getResult());
    }
}
