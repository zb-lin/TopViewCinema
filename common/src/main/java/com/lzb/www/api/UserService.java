package com.lzb.www.api;


public interface UserService {

    byte[] login(byte[] bytes);

    byte[] getUser(byte[] bytes);

    byte[] changePassword(byte[] bytes);

    byte[] uploadHead(byte[] bytes);
}
