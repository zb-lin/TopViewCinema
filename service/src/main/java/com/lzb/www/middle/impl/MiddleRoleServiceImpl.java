package com.lzb.www.middle.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lzb.www.middle.MiddleRoleService;
import com.lzb.www.proto.RPCProto;
import com.lzb.www.service.RoleService;
import sspring.bean.annotation.Hosted;
import sspring.bean.annotation.LoggerProxy;

import java.util.Arrays;

@Hosted
public class MiddleRoleServiceImpl implements MiddleRoleService {
    @LoggerProxy
    private RoleService roleService;

    @Override
    public byte[] listPermission(byte[] bytes) throws InvalidProtocolBufferException {
        RPCProto.RoleRequest roleRequest = RPCProto.RoleRequest.parseFrom(bytes);
        RPCProto.RoleRequest.ListPermissionInfo listPermissionInfo = roleRequest.getListPermissionInfo();
        int id = listPermissionInfo.getId();
        String[] strings = roleService.listPermission(id);
        RPCProto.RoleResponse.ListPermissionResult.Builder listPermissionResult = RPCProto.RoleResponse.ListPermissionResult.newBuilder();
        for (String s : strings) {
            listPermissionResult.addPermission(s);
        }
        return RPCProto.RoleResponse.newBuilder().setListPermissionResult(listPermissionResult).build().toByteArray();
    }

}
