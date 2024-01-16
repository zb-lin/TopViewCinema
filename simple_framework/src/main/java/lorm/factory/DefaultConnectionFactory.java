package lorm.factory;

import java.sql.Connection;

/**
 * 方法实现类
 */
public class DefaultConnectionFactory extends AbstractDataSourceConnectionFactory {

    private static final DefaultConnectionFactory factory = new DefaultConnectionFactory();

    protected DefaultConnectionFactory getFactory() {
        return factory;
    }

    /**
     * 获得Connection对象
     *
     * @return Connection对象
     */
    public Connection getConnection() {
        return super.getConnection();
    }

    public void close() {
        factory.closeConnection();
    }
}
