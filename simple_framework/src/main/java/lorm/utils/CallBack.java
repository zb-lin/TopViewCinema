package lorm.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface CallBack {
    Object doExecute(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet);
}
