package com.lzb.www.middle.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lzb.www.middle.MiddleUserService;
import com.lzb.www.pojo.po.User;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.service.UserService;
import sspring.bean.annotation.Hosted;
import sspring.bean.annotation.LoggerProxy;

@Hosted
public class MiddleUserServiceImpl implements MiddleUserService {
    @LoggerProxy
    private UserService userService;

    @Override
    public byte[] login(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.UserRequest userRequest = RPCProto.UserRequest.parseFrom(bytes);
        RPCProto.UserRequest.LoginInfo loginInfo = userRequest.getLoginInfo();
        String token = userService.login(loginInfo.getPhone(), loginInfo.getPassword());

        RPCProto.UserResponse.LoginResult loginResult = RPCProto.UserResponse.LoginResult.newBuilder()
                .setResult(token).build();
        return RPCProto.UserResponse.newBuilder().setLoginResult(loginResult).build().toByteArray();
    }

    @Override
    public byte[] getUser(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.UserRequest userRequest = RPCProto.UserRequest.parseFrom(bytes);
        RPCProto.UserRequest.GetUserInfo getUserInfo = userRequest.getGetUserInfo();
        User user = userService.getUser(Integer.parseInt(getUserInfo.getId()));

        RPCProto.User user1 = RPCProto.User.newBuilder()
                .setId(user.getId())
                .setPassword(user.getPassword())
                .setPhone(user.getPhone())
                .setHead(user.getHead())
                .build();

        RPCProto.UserResponse.GetUserResult getUserResult = RPCProto.UserResponse.GetUserResult.newBuilder()
                .setUser(user1).build();
        return RPCProto.UserResponse.newBuilder().setGetUserResult(getUserResult).build().toByteArray();
    }

    @Override
    public byte[] changePassword(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.UserRequest userRequest = RPCProto.UserRequest.parseFrom(bytes);
        RPCProto.UserRequest.ChangePasswordInfo changePasswordInfo = userRequest.getChangePasswordInfo();
        RPCProto.User user = changePasswordInfo.getUser();
        User user1 = new User(user.getId(), user.getPhone(), user.getPassword(), user.getHead());

        boolean result = userService.changePassword(user1);

        RPCProto.UserResponse.ChangePasswordResult changePasswordResult = RPCProto.UserResponse.ChangePasswordResult.newBuilder()
                .setResult(result).build();
        return RPCProto.UserResponse.newBuilder().setChangePasswordResult(changePasswordResult).build().toByteArray();
    }

    @Override
    public byte[] uploadHead(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.UserRequest userRequest = RPCProto.UserRequest.parseFrom(bytes);
        RPCProto.UserRequest.UploadHeadInfo uploadHeadInfo = userRequest.getUploadHeadInfo();

        boolean result = userService.uploadHead(uploadHeadInfo.getFileName(), uploadHeadInfo.getId());

        RPCProto.UserResponse.UploadHeadResult uploadHeadResult = RPCProto.UserResponse.UploadHeadResult.newBuilder()
                .setResult(result).build();
        return RPCProto.UserResponse.newBuilder().setUploadHeadResult(uploadHeadResult).build().toByteArray();
    }


}
