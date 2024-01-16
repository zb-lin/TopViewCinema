package lorm.config;

/**
 * 管理配置信息
 *
 * @author lzb
 * @since 2023/3/4
 */
public class Configuration {
    /**
     * 驱动类
     */
    private String driver;
    /**
     * jdbc的url
     */
    private String url;
    /**
     * 数据库的用户名
     */
    private String username;
    /**
     * 数据库的密码
     */
    private String password;
    /**
     * Query路径
     */
    private String queryClass;
    /**
     * 连接池中最小的连接数
     */
    private Integer poolMinSize;

    /**
     * 连接池中最大的连接数
     */
    private Integer poolMaxSize;

    public Configuration(String driver, String url, String username, String password, String queryClass) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.queryClass = queryClass;
    }

    public Configuration() {
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(String queryClass) {
        this.queryClass = queryClass;
    }

    public int getPoolMinSize() {
        return poolMinSize;
    }

    public void setPoolMinSize(Integer poolMinSize) {
        this.poolMinSize = poolMinSize;
    }

    public int getPoolMaxSize() {
        return poolMaxSize;
    }

    public void setPoolMaxSize(Integer poolMaxSize) {
        this.poolMaxSize = poolMaxSize;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", queryClass='" + queryClass + '\'' +
                ", poolMinSize=" + poolMinSize +
                ", poolMaxSize=" + poolMaxSize +
                '}';
    }
}
