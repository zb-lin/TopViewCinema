package com.lzb.www.dao;

import com.lzb.www.pojo.po.Role;

import java.util.List;

/**
 * @author lzb
 */
public interface RoleDao {

    /**
     * 根据id得到权限
     *
     * @param id 权限id
     */
    Role getPermissionsById(int id);

    /**
     * 根据id得到权限
     */
    List<Object> getActionById(Object[] ids);
}
