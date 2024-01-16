package com.lzb.www.service;

import com.lzb.www.pojo.po.Role;

import java.util.List;

public interface RoleService {
    /**
     * 根据id查找权限
     *
     * @param roleId 角色id
     * @return 权限数组
     */
    String[] listPermission(int roleId);

}
