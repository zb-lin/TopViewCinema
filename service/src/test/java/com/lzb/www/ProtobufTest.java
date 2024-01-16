package com.lzb.www;


import com.google.protobuf.InvalidProtocolBufferException;
import com.lzb.www.middle.MiddleUserService;
import com.lzb.www.middle.impl.MiddleUserServiceImpl;
import com.lzb.www.proto.RPCProto;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ProtobufTest {
    @Test
    public void test01() throws InvalidProtocolBufferException {
        byte[] bytes = (byte[]) test02();
        System.out.println(Arrays.toString(bytes));

    }


//    private final MiddleUserService middleUserService = ProxyFactory.getProxy(MiddleUserService.class);

    @Test
    public void test03() throws InvalidProtocolBufferException {
//        InvokeData invokeData = new InvokeData();
//        invokeData.setRpcRequestByteArray(test02());
//        InvokeDataProto.InvokeData builder = InvokeDataProto.InvokeData.newBuilder()
//                .setRpcRequestByteArray(ByteString.copyFrom((byte[]) invokeData.getRpcRequestByteArray())).build();
//
//        byte[] byteArray = builder.toByteArray();
//
//        InvokeDataProto.InvokeData invokeData1 = InvokeDataProto.InvokeData.parseFrom(byteArray);
//        ByteString rpcRequestByteArray = invokeData1.getRpcRequestByteArray();
//
//        byte[] byteArray1 = rpcRequestByteArray.toByteArray();
//        System.out.println(Arrays.toString(byteArray1));
    }

    MiddleUserService middleUserService = new MiddleUserServiceImpl();

    public Object test02() {
        byte[] bytes = {1, 2, 3, 4, 5, 6};
        System.out.println(Arrays.toString(bytes));
        return bytes;
    }

    @Test
    public void test04() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends MiddleUserService> aClass = middleUserService.getClass();
        Method[] methods = aClass.getMethods();
        System.out.println(Arrays.toString(methods));

        Method method = aClass.getMethod("login", byte[].class);
        method.invoke(middleUserService, test02());
    }

    @Test
    public void test05() throws InvalidProtocolBufferException {
        RPCProto.UserRequest.LoginInfo loginInfo = RPCProto.UserRequest.LoginInfo.newBuilder()
                .setPhone("admin")
                .setPassword("admin").build();
        RPCProto.UserRequest userRequest = RPCProto.UserRequest.newBuilder().setLoginInfo(loginInfo).build();
        byte[] byteArray = userRequest.toByteArray();


        RPCProto.UserRequest userRequest1 = RPCProto.UserRequest.parseFrom(byteArray);
//        RPCProto.UserRequest.GetUserInfo getUserInfo = userRequest1.getGetUserInfo();
//        String id = getUserInfo.getId();  // 不存在 ""
//        if ("".equals(id)) {
//            System.out.println(1);
//        }
//        System.out.println(id);
        int id = userRequest1.getUploadHeadInfo().getId();
        System.out.println(id);


    }
}
