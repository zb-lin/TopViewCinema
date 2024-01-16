package com.lzb.www.middle;

import com.google.protobuf.InvalidProtocolBufferException;

public interface MiddleRoleService {
    /**
     * 根据id查找权限
     */
    byte[] listPermission(byte[] bytes) throws InvalidProtocolBufferException;
}
