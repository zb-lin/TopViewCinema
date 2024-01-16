package lorm.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface ConnectionFactory {
    /**
     * 获得Connection对象
     *
     * @return Connection对象
     */
    Connection getConnection();

    void close(ResultSet resultSet, PreparedStatement preparedStatement);

    void close(PreparedStatement preparedStatement);

    void close();

}
