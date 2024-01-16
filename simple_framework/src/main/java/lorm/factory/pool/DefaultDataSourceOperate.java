package lorm.factory.pool;


import java.sql.Connection;
import java.sql.SQLException;

import static lorm.constant.ConfigurationConstant.CONNECTIONS_ADDED_EACH_TIME;
import static lorm.constant.ConfigurationConstant.INT_ONE;

public class DefaultDataSourceOperate extends AbstractDataSource {

    /**
     * 从连接池中取出一个连接
     *
     * @return conn连接
     */
    public Connection getConnection() {
        if (pool.isEmpty()) {
            for (int i = 0; i < CONNECTIONS_ADDED_EACH_TIME; i++) {
                pool.add(createConnection());
            }
        }
        Connection connection = pool.remove(pool.size() - INT_ONE);
        usedPool.putIfAbsent(Thread.currentThread().getId(), connection);
        return usedPool.get(Thread.currentThread().getId());
    }


    /**
     * 将连接放回池中
     */
    public void closeConnection() {
        pool.add(usedPool.remove(Thread.currentThread().getId()));
        if (pool.size() < POOL_MAX_SIZE) {
            return;
        }
        while (pool.size() >= POOL_MAX_SIZE) {
            try {
                if (pool.get(pool.size() - INT_ONE) != null) {
                    pool.remove(pool.size() - INT_ONE).close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
