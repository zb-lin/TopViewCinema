package com.lzb.www.controller.mock;

import com.lzb.www.api.UserService;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.util.JWTUtils;
import sspring.bean.annotation.Hosted;

import java.util.HashMap;
import java.util.Map;

import static com.lzb.www.constant.WebConstant.ID;

@Hosted
public class MockUserServiceImpl implements UserService {


    public byte[] login(byte[] bytes) {
        Map<String, Integer> map = new HashMap<>();
        map.put(ID, 1);
        String token = JWTUtils.getToken(map);

        RPCProto.UserResponse.LoginResult loginResult = RPCProto.UserResponse.LoginResult.newBuilder()
                .setResult(token).build();
        return RPCProto.UserResponse.newBuilder().setLoginResult(loginResult).build().toByteArray();
    }

    public byte[] getUser(byte[] bytes) {
        RPCProto.User user = RPCProto.User.newBuilder()
                .setId(1)
                .setPassword("admin")
                .setPhone("admin")
                .setHead("http://localhost:8080/TopViewCinema/upload/2023-05-03-09-02-02head.jpg")
                .build();

        RPCProto.UserResponse.GetUserResult getUserResult = RPCProto.UserResponse.GetUserResult.newBuilder()
                .setUser(user).build();
        return RPCProto.UserResponse.newBuilder().setGetUserResult(getUserResult).build().toByteArray();
    }

    public byte[] changePassword(byte[] bytes) {
        RPCProto.UserResponse.ChangePasswordResult changePasswordResult = RPCProto.UserResponse.ChangePasswordResult.newBuilder()
                .setResult(true).build();
        return RPCProto.UserResponse.newBuilder().setChangePasswordResult(changePasswordResult).build().toByteArray();
    }

    public byte[] uploadHead(byte[] bytes) {
        RPCProto.UserResponse.UploadHeadResult uploadHeadResult = RPCProto.UserResponse.UploadHeadResult.newBuilder()
                .setResult(true).build();
        return RPCProto.UserResponse.newBuilder().setUploadHeadResult(uploadHeadResult).build().toByteArray();
    }


}
