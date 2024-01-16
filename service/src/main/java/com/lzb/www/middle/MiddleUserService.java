package com.lzb.www.middle;


import com.google.protobuf.InvalidProtocolBufferException;

public interface MiddleUserService {

    byte[] login(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] getUser(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] changePassword(byte[] bytes) throws InvalidProtocolBufferException;

    byte[] uploadHead(byte[] bytes) throws InvalidProtocolBufferException;

}
