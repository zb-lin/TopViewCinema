package com.lzb.www.middle;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ServiceException;

public interface MiddleMovieService {
    byte[] insertMovie(byte[] bytes) throws InvalidProtocolBufferException, ServiceException;

    byte[] listMovies(byte[] bytes) throws InvalidProtocolBufferException, ServiceException;

    byte[] uploadPoster(byte[] bytes) throws InvalidProtocolBufferException, ServiceException;

    byte[] updateMovie(byte[] bytes) throws InvalidProtocolBufferException, ServiceException;

    byte[] deleteMovie(byte[] bytes) throws InvalidProtocolBufferException, ServiceException;

    byte[] listMoviesByParam(byte[] bytes) throws InvalidProtocolBufferException, ServiceException;

    byte[] updateEvaluate(byte[] bytes) throws InvalidProtocolBufferException;
}
