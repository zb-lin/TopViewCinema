package com.lzb.www.dao.impl;

import com.lzb.www.dao.UserDao;
import com.lzb.www.pojo.po.User;
import sspring.bean.annotation.Hosted;

import java.util.List;
import java.util.Optional;

/**
 * @author lzb
 */
@Hosted
public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public Optional<User> getUserByUsernameAndPassword(String phone, String password) {
        String sql = "select * from tb_user where phone = ? and password = ?";
        List<Object> users = query.queryRows(sql, User.class, new Object[]{phone, password});
        return Optional.ofNullable(users).isPresent() ? Optional.ofNullable((User) users.get(0)) : Optional.empty();
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        String sql = "select * from tb_user where id = ? ";
        List<Object> users = query.queryRows(sql, User.class, new Object[]{id});
        return Optional.ofNullable(users).isPresent() ? Optional.ofNullable((User) users.get(0)) : Optional.empty();
    }

    @Override
    public boolean insertUser(User user) {
        return query.insert(user);
    }

    @Override
    public boolean updateUser(Object obj, String[] fieldNames) {
        return query.update(obj, fieldNames);
    }

    @Override
    public Optional<User> getUserByPhone(String phone) {
        String sql = "select * from tb_user where phone = ? ";
        List<Object> users = query.queryRows(sql, User.class, new Object[]{phone});
        return Optional.ofNullable(users).isPresent() ? Optional.ofNullable((User) users.get(0)) : Optional.empty();
    }

    @Override
    public boolean updateHead(String headURL, int id) {
        String sql = "update tb_user set head = ? where id = ?";
        return query.update(sql, new Object[]{headURL, String.valueOf(id)});
    }


}
