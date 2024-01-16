package com.lzb.www.util.type;


import com.lzb.www.exception.WebException;
import com.lzb.www.util.StringUtils;

import java.sql.SQLException;

public class ArrayTypeHandler implements TypeHandler<String> {

    @Override
    public String getResult(String param) throws SQLException {
        if (Boolean.FALSE.equals(StringUtils.isNotEmpty(param))) {
            throw new WebException(param + " is null");
        }
        return param.substring(1, param.length() - 1);
    }

    @Override
    public String getResult(String param, Class<?> beanClass) throws SQLException {
        return getResult(param);
    }
}
