package com.lzb.www.pojo.po;


import lorm.annotation.ColumnName;
import lorm.annotation.TableName;

/**
 * 权限表
 */
@TableName("tb_permission")
public class Permission {
    /**
     * 权限id
     */
    private Integer id;
    /**
     * 权限名称
     */
    @ColumnName("permission_name")
    private String permissionName;

    /**
     * 执行动作
     */
    @ColumnName("route_action")
    private String routeAction;


    public Permission(Integer id, String permissionName,
                      String routeAction) {
        this.id = id;
        this.permissionName = permissionName;
        this.routeAction = routeAction;
    }

    public Permission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }


    public String getRouteAction() {
        return routeAction;
    }

    public void setRouteAction(String routeAction) {
        this.routeAction = routeAction;
    }


    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                ", routeAction='" + routeAction + '\'' +
                '}';
    }
}
