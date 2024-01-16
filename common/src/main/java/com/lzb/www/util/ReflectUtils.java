package com.lzb.www.util;

import java.lang.reflect.Method;

/**
 * 封装反射常用操作
 *
 * @author lzb
 */
public class ReflectUtils {
    private ReflectUtils() {
    }

    /**
     * 调用obj对象对应属性fieldName的get方法
     *
     * @param fieldName 属性名
     * @param obj 传入对象
     */
    public static Object invokeGet(String fieldName, Object obj) {
        try {
            Class<?> objClass = obj.getClass();
            Method method = objClass.getDeclaredMethod("get" + StringUtils.firstCharToUpperCase(fieldName),  null);
            return method.invoke(obj, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void invokeSet(Object obj, String columnName, Object columnValue) {
        try {
            if (columnValue != null) {
                Method method = obj.getClass()
                        .getDeclaredMethod("set" + StringUtils.firstCharToUpperCase(columnName),
                                columnValue.getClass());
                method.invoke(obj, columnValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
