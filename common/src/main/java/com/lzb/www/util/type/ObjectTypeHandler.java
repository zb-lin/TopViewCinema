package com.lzb.www.util.type;

import com.alibaba.fastjson.JSON;
import com.lzb.www.exception.WebException;
import com.lzb.www.util.ReflectUtils;
import com.lzb.www.util.type.annotation.NotEmpty;
import com.lzb.www.util.type.annotation.NotNull;
import com.lzb.www.util.type.annotation.Range;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class ObjectTypeHandler implements TypeHandler<Object> {

    @Override
    public Object getResult(String param) throws SQLException {
        return null;
    }

    @Override
    public Object getResult(String param, Class<?> beanClass) throws SQLException {
        Field[] fields = beanClass.getDeclaredFields();
        Object object = JSON.parseObject(param, beanClass);
        Arrays.stream(fields).forEach(field -> {
            Object value = ReflectUtils.invokeGet(field.getName(), object);
            NotNull notNull = field.getAnnotation(NotNull.class);
            NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
            Range range = field.getAnnotation(Range.class);
            if (notNull != null && value == null) {
                String msg = notNull.value();
                if ("".equals(msg)) {
                    msg = field.getName() + " cannot be empty";
                }
                throw new WebException(beanClass.getSimpleName() + ": " + msg);
            }
            if (notEmpty != null && (value == null || value.toString().isEmpty())) {
                String msg = notEmpty.value();
                if ("".equals(msg)) {
                    msg = field.getName() + " cannot be empty";
                }
                throw new WebException(beanClass.getSimpleName() + ": " + msg);
            }
            if (range != null) {
                if (range.max() == -1 && range.min() > Integer.parseInt(String.valueOf(value))) {
                    throw new WebException(beanClass.getSimpleName() + ": " + field.getName() + " not in range");
                } else if (range.min() > Integer.parseInt(String.valueOf(value))
                        || Integer.parseInt(String.valueOf(value)) > range.max()) {
                    throw new WebException(beanClass.getSimpleName() + ": " + field.getName() + " not in range");
                }
            }
        });
        return object;
    }


}
