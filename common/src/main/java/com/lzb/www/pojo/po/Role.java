package com.lzb.www.pojo.po;

import com.lzb.www.util.type.annotation.NotEmpty;
import lorm.annotation.TableName;

/**
 * 角色表
 */
@TableName("tb_role")
public class Role {
    /**
     * 角色id
     */
    private Integer id;
    /**
     * 角色名称
     */
    @NotEmpty("name not empty")
    private String name;

    /**
     * 角色权限
     */
    private String permission;

    public Role(Integer id, String name, String permission) {
        this.id = id;
        this.name = name;
        this.permission = permission;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
