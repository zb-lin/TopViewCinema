package com.lzb.www.service.impl;

import com.lzb.www.dao.RoleDao;
import com.lzb.www.pojo.po.Role;
import com.lzb.www.service.RoleService;
import lorm.utils.ReflectUtils;
import sspring.bean.annotation.Autowired;
import sspring.bean.annotation.Hosted;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Hosted
public class RoleServiceImpl implements RoleService {
    private static final String ROUTE_ACTION = "routeAction";
    @Autowired
    private RoleDao roleDao;
    private final Map<Integer, String[]> permissionsMap = new HashMap<>();

    @Override
    public String[] listPermission(int roleId) {
        if (permissionsMap.get(roleId) != null) {
            return permissionsMap.get(roleId);
        }
        // 得到角色对象
        Role role = roleDao.getPermissionsById(roleId);
        // 得到权限id集合
        String[] permissions = role.getPermission().replace(" ", "").split(",");
        // 得到权限集合
        List<Object> actions = roleDao.getActionById(permissions);
        StringBuilder stringBuilder = new StringBuilder();
        for (Object action : actions) {
            String routeAction = (String) ReflectUtils.invokeGet(ROUTE_ACTION, action);
            stringBuilder.append(routeAction).append(",");
        }
        String[] roleActions = stringBuilder.toString().replace(" ", "").split(",");
        permissionsMap.put(roleId, roleActions);
        return roleActions;
    }



}
