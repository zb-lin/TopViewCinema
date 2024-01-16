package com.lzb.www.dao.impl;

import com.lzb.www.dao.RoleDao;
import com.lzb.www.pojo.po.Permission;
import com.lzb.www.pojo.po.Role;
import sspring.bean.annotation.Hosted;

import java.util.List;
import java.util.Optional;

/**
 * @author lzb
 */

@Hosted
public class RoleDaoImpl extends BaseDao implements RoleDao {

    @Override
    public Role getPermissionsById(int id) {
        String sql = "select permission from tb_role where id =?";
        List<Object> roles = query.queryRows(sql, Role.class, new Object[]{id});
        return Optional.ofNullable(roles).isPresent() ? (Role) roles.get(0) : null;
    }

    @Override
    public List<Object> getActionById(Object[] ids) {
        StringBuilder sql = new StringBuilder("select route_action from tb_permission where ");
        for (int i = 0; i < ids.length; i++) {
            sql.append(" id = ? or");
        }
        sql.delete(sql.length() - 2, sql.length());
        return query.queryRows(sql.toString(), Permission.class, ids);
    }

}
