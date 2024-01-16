package com.lzb.www.util.type;

import java.sql.SQLException;

/**
 * 类型处理器接口
 */
public interface TypeHandler<T> {

    /**
     * 获取对应字段的值
     */
    T getResult(String param) throws SQLException;

    /**
     * 获取对应字段的值
     */
    T getResult(String param, Class<?> beanClass) throws SQLException;
}
