package com.lzb.www.service.impl;


import com.lzb.www.dao.UserDao;
import com.lzb.www.pojo.po.User;
import com.lzb.www.service.UserService;
import com.lzb.www.util.JWTUtils;
import com.lzb.www.util.StringUtils;
import sspring.bean.annotation.Autowired;
import sspring.bean.annotation.Hosted;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.lzb.www.constant.GlobalConstant.*;
import static com.lzb.www.constant.WebConstant.ID;

@Hosted
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private static final Map<Integer, User> userMap = new HashMap<>();

    @Override
    public String login(String phone, String password) {
        if (!StringUtils.isPhone(phone)) {
            if (!(ADMIN.equals(phone) && ADMIN.equals(password))) {
                return null;
            }
        }
        Optional<User> optionalUser = userDao.getUserByUsernameAndPassword(phone, password);
        User user = optionalUser.orElseGet(() -> {
            // 为空自动注册
            Optional<User> userByPhone = userDao.getUserByPhone(phone);
            // 根据手机号查询用户
            return userByPhone.orElseGet(() -> {
                // 不存在则注册
                User newUser = new User(null, phone, password, DEFAULT_HEAD);
                if (!userDao.insertUser(newUser)) {
                    throw new RuntimeException("insert error");
                }
                return newUser;
            });
        });
        // 获得的用户密码和输入的密码不一样, 即密码错误
        if (!user.getPassword().equals(password)) {
            return null;
        }
        // 生成token
        try {
            Map<String, Integer> map = new HashMap<>();
            map.put(ID, user.getId());
            return JWTUtils.getToken(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(Integer id) {
        // 先从缓存中取, 不存在查询数据库
        if (userMap.get(id) != null) {
            return userMap.get(id);
        }
        User user = userDao.getUserById(id).orElse(null);
        userMap.put(id, user);
        return user;
    }

    @Override
    public boolean changePassword(User user) {
        int id = user.getId();
        Optional<User> optionalUser = userDao.getUserById(id);
        User userById = optionalUser.orElseThrow(() -> new RuntimeException("user is null"));
        if (userById.getPassword().equals(user.getPassword())) {
            return true;
        }
        if (!userDao.updateUser(user, new String[]{PASSWORD})) {
            return false;
        }
        if (userMap.get(id) != null) {
            userMap.put(id, user);
        }
        return true;
    }

    @Override
    public boolean uploadHead(String fileName, int id) {
        String headURL = UPLOAD_IMG_PATH + fileName;
        if (!userDao.updateHead(headURL, id)) {
            return false;
        }
        if (userMap.get(id) != null) {
            User user = userMap.get(id);
            user.setHead(headURL);
            userMap.put(id, user);
        }
        return true;
    }

}
