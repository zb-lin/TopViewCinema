package com.lzb.www;

import org.junit.jupiter.api.Test;
import rpc.util.RedisLock;

public class SpringTest {
    @Test
    public void test() throws ClassNotFoundException, NoSuchFieldException {

//        Class<?> valueClass = Class.forName("com.lzb.www.controller.UserServlet");
//        System.out.println(valueClass);
//
//        Field field = valueClass.getDeclaredField("userService");
//
//        System.out.println(field.getType());
//        Class<?> fieldClass = Class.forName("com.lzb.www.api.UserService");
//        System.out.println(fieldClass);

        String packageName = "com.lzb.www.api.UserService";
        int index = packageName.lastIndexOf(".");
        System.out.println(index);
        String beanName = packageName.substring(index + 1);
        System.out.println(beanName);


    }

    @Test
    public void test01() {
        boolean lock = RedisLock.getLock(1L, "132");
        System.out.println(lock);
        boolean result = RedisLock.releaseLock(1L, "132");
        System.out.println(result);
    }

}
