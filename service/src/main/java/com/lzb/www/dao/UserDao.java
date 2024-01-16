package com.lzb.www.dao;


import com.lzb.www.pojo.po.User;

import java.util.Optional;

/**
 * 用户信息操作接口
 */
public interface UserDao {
    /**
     * 登录
     *
     * @param phone    用户名
     * @param password 密码
     * @return user实体类
     */
    Optional<User> getUserByUsernameAndPassword(String phone, String password);

    Optional<User> getUserById(Integer id);

    boolean insertUser(User user);

    boolean updateUser(Object obj, String[] fieldNames);

    Optional<User> getUserByPhone(String phone);

    boolean updateHead(String headURL, int id);
}
