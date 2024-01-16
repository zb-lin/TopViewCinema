package com.lzb.www.controller;

import com.lzb.www.annotation.CookieParams;
import com.lzb.www.annotation.File;
import com.lzb.www.annotation.Param;
import com.lzb.www.api.UserService;
import com.lzb.www.exception.WebException;
import com.lzb.www.pojo.po.User;
import com.lzb.www.pojo.vo.Response;
import com.lzb.www.pojo.vo.UserLoginVo;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.util.ProtoBufUtil;
import sspring.bean.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lzb.www.constant.GlobalConstant.*;
import static com.lzb.www.constant.WebConstant.*;
import static com.lzb.www.constant.WebConstant.ID;
import static com.lzb.www.constant.WebConstant.TOKEN;
import static com.lzb.www.constant.WebConstant.UTF_8;

@Hosted
@WebServlet("/user/*")
@MultipartConfig(location = UPLOAD_IMG_FOLDER_URL, maxFileSize = MAX_FILE_SIZE)
public class UserServlet extends BaseServlet {

    @RpcProxy
//    @MockBean
    private UserService userService;


    /**
     * 登录
     */
    public Response<?> login(@Param UserLoginVo userLoginVo) throws IOException {
        RPCProto.UserRequest userRequest = ProtoBufUtil.loginInfoRequest(
                userLoginVo.getPhone().trim(), userLoginVo.getPassword().trim());
        byte[] bytes = userService.login(userRequest.toByteArray());
        String token = ProtoBufUtil.loginResponse(bytes);

        return Response.response(userLoginVo.getPhone(), LOGIN_SUCCESS, LOGIN_ERROR, bytes != null, () -> {
            Cookie cookie = null;
            try {
                cookie = new Cookie(TOKEN, URLEncoder.encode(token, UTF_8));
                cookie.setMaxAge(ONE_DAY);
                cookie.setPath("/");
                return cookie;
            } catch (UnsupportedEncodingException e) {
                throw new WebException(e);
            }
        });
    }


    /**
     * 根据Token获取user对象
     */
    public Response<?> getUserByToken(@CookieParams Integer id) throws IOException {
        Optional.ofNullable(id).orElseThrow(() -> new WebException(NOT_LOGIN));

        RPCProto.UserRequest userRequest = ProtoBufUtil.getUserInfoRequest(id);
        byte[] bytes = userService.getUser(userRequest.toByteArray());
        User user = ProtoBufUtil.getUser(bytes);

        return Response.response(user, QUERY_SUCCESS, NOT_LOGIN, bytes != null);
    }

    public Response<?> changePassword(@Param User user) throws IOException {
        RPCProto.UserRequest userRequest = ProtoBufUtil.changePasswordRequest(user);
        byte[] bytes = userService.changePassword(userRequest.toByteArray());
        return Response.response(UPDATE_SUCCESS, UPDATE_ERROR, ProtoBufUtil.changePasswordResponse(bytes));
    }

    public Response<?> uploadHead(@File Part file, @Param(ID) Integer id) throws IOException, ServletException {
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)) + file.getSubmittedFileName();
        file.write(fileName);

        RPCProto.UserRequest userRequest = ProtoBufUtil.uploadHeadRequest(fileName, id);
        byte[] bytes = userService.uploadHead(userRequest.toByteArray());
        RPCProto.UserResponse userResponse = RPCProto.UserResponse.parseFrom(bytes);
        return Response.response(SUMMIT_SUCCESS, SUMMIT_ERROR, userResponse.getUploadHeadResult().getResult());
    }
}
