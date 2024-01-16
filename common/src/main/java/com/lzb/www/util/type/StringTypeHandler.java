package com.lzb.www.util.type;


import com.lzb.www.exception.WebException;
import com.lzb.www.util.StringUtils;

import java.sql.SQLException;

public class StringTypeHandler implements TypeHandler<String> {

    @Override
    public String getResult(String param) throws SQLException {
        if (Boolean.FALSE.equals(StringUtils.isNotEmpty(param))) {
            throw new WebException(param + " is null");
        }
        return param;
    }

    @Override
    public String getResult(String param, Class<?> beanClass) throws SQLException {
        return getResult(param);
    }
}
