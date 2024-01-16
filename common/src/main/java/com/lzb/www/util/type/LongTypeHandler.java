package com.lzb.www.util.type;

import com.lzb.www.exception.WebException;
import com.lzb.www.util.StringUtils;

import java.sql.SQLException;

public class LongTypeHandler implements TypeHandler<Long> {

    @Override
    public Long getResult(String param) throws SQLException {
        if (Boolean.FALSE.equals(StringUtils.isNotEmpty(param))) {
            throw new WebException(param + " is null");
        }
        if (!StringUtils.isNumeric(param)) {
            throw new WebException(param + " is NaN");
        }
        return Long.valueOf(param);
    }

    @Override
    public Long getResult(String param, Class<?> beanClass) throws SQLException {
        return getResult(param);
    }
}
