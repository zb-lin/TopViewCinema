package lorm.aop;


import lorm.factory.ConnectionFactory;
import lorm.factory.DefaultConnectionFactory;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * 通过动态代理实现数据库事务
 *
 * @author lzb
 */
public class TransactionHandler implements AbstractHandler {
    private final Object target;

    public TransactionHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ConnectionFactory factory = new DefaultConnectionFactory();
        Connection connection = factory.getConnection();
        connection.setAutoCommit(false);
        Object result = null;
        try {
            result = method.invoke(target, args);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            factory.close();
        }
        return result;
    }
}
