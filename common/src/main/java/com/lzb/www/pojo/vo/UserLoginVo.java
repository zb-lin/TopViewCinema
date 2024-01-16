package com.lzb.www.pojo.vo;

/**
 * 用户登录bo类
 */
public class UserLoginVo {
    /**
     * 用户名
     */
    private String phone;
    /**
     * 密码
     */
    private String password;

    public UserLoginVo(String username, String password) {
        this.phone = username;
        this.password = password;
    }

    public UserLoginVo() {
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

    @Override
    public String toString() {
        return "UserLoginBo{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
