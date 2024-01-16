package lorm.factory.pool;

import lorm.factory.xml.XmlParse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 连接池的类
 *
 * @author lzb
 */
public abstract class AbstractDataSource implements MyDataSource {

    /**
     * 连接池对象
     */
    protected static final List<Connection> pool = new CopyOnWriteArrayList<>();
    /**
     * 正在使用中的连接的连接池
     */
    protected static ConcurrentHashMap<Long, Connection> usedPool = new ConcurrentHashMap<>();
    /**
     * 最大连接数
     */
    private static final XmlParse xmlParse = new XmlParse();

    protected static final int POOL_MAX_SIZE = xmlParse.getConfiguration().getPoolMaxSize();
    /**
     * 最小连接数
     */
    protected static final int POOL_MIN_SIZE = xmlParse.getConfiguration().getPoolMinSize();

    /**
     * 构造器创建连接池
     */
    protected AbstractDataSource() {
        initPool();
    }

    /**
     * 初始化连接池, 使池中连接数达到最小值
     */
    public void initPool() {
        while (pool.size() < AbstractDataSource.POOL_MIN_SIZE) {
            pool.add(createConnection());
        }
    }

    /**
     * 创建新的Connection对象
     */
    protected Connection createConnection() {
        try {
            Class.forName(xmlParse.getConfiguration().getDriver());
            return DriverManager.getConnection(xmlParse.getConfiguration().getUrl(),
                    xmlParse.getConfiguration().getUsername(), xmlParse.getConfiguration().getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 从连接池中取出一个连接
     *
     * @return conn连接
     */
    abstract Connection getConnection();

    /**
     * 将连接放回池中
     */
    abstract void closeConnection();


}
