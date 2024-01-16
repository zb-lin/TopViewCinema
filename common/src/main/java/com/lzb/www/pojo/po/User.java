package com.lzb.www.pojo.po;

import com.lzb.www.util.type.annotation.NotEmpty;
import com.lzb.www.util.type.annotation.NotNull;
import lorm.annotation.TableName;


@TableName("tb_user")
public class User {
    /**
     * 用户ID(自增)
     */
    @NotNull
    private Integer id;
    /**
     * 电话号码(相当于 username)
     */
    @NotEmpty
    private String phone;
    /**
     * 密码
     */
    @NotEmpty
    private String password;

    /**
     * 头像路径
     */
    @NotEmpty
    private String head;

    public User(Integer id, String phone, String password, String head) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.head = head;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}
