package com.lzb.www.service;


import com.lzb.www.pojo.po.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录是否成功
     */
    String login(String username, String password);

    /**
     * 根据id获得用户信息
     *
     * @param id 用户id
     * @return 用户对象
     */
    User getUser(Integer id);

    /**
     * 修改密码
     *
     * @param user 修改密码的用户对象
     * @return 是否修改成功
     */
    boolean changePassword(User user);

    /**
     * 上传头像
     *
     * @param fileName 图片路径
     * @param id       用户id
     * @return 是否更新成功
     */
    boolean uploadHead(String fileName, int id);
}
