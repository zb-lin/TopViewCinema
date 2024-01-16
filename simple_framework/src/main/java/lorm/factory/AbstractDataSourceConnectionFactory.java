package lorm.factory;


import lorm.factory.pool.DefaultDataSourceOperate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 根据配置信息, 维持连接对象的管理(增加连接池的功能)
 *
 * @author lzb
 */
public abstract class AbstractDataSourceConnectionFactory extends DefaultDataSourceOperate implements ConnectionFactory {
    public void close(ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
