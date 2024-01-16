package lorm.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 封装jdbc查询常用操作
 *
 * @author lzb
 */
public class JDBCUtils {
    /**
     * 给sql设参
     *
     * @param preparedStatement     预编译sql语句对象
     * @param params 参数
     */
    public static void handleParams(PreparedStatement preparedStatement, Object[] params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                try {
                    preparedStatement.setObject(i + 1, params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
