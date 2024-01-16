package lorm.factory.query;


import lorm.exception.DaoException;
import lorm.factory.ConnectionFactory;
import lorm.factory.DefaultConnectionFactory;
import lorm.utils.CallBack;
import lorm.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class QueryTemplate extends DefaultConnectionFactory implements Query {

    private final ConnectionFactory factory = getFactory();

    /**
     * 采用模板方法模式将JDBC操作封装成模板, 便于重用
     *
     * @param sql    sql语句
     * @param params sql的参数
     * @param back   CallBack的实现类
     */
    protected Object executeQueryTemplate(String sql, Object[] params, CallBack back) {
        Connection connection = factory.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            // 给sql设参
            JDBCUtils.handleParams(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
            // 回调函数
            return back.doExecute(connection, preparedStatement, resultSet);
        } catch (Exception e) {
            throw new DaoException("数据库初始化异常", e);
        } finally {
            factory.close(resultSet, preparedStatement);
        }
    }

    /**
     * 直接执行一个DML语句
     *
     * @param sql    sql语句
     * @param params 参数
     * @return 执行sql语句后影响记录的行数
     */
    protected boolean executeDML(String sql, Object[] params) {
        Connection connection = factory.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            assert connection != null;
            preparedStatement = connection.prepareStatement(sql);
            // 给sql设参
            JDBCUtils.handleParams(preparedStatement, params);
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            throw new DaoException("数据库操作执行异常", e);
        } finally {
            factory.close(preparedStatement);
        }
    }
}
