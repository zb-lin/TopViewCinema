package rpc.config;

import java.time.LocalDateTime;

public class ServiceProvider {
    /**
     * 服务接口名
     */
    private String serviceInterface;
    /**
     * 服务提供者ip地址
     */
    private String hostname;
    /**
     * 服务端口号
     */
    private Integer port;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public ServiceProvider(String serviceInterface, String hostname, Integer port, LocalDateTime expireTime) {
        this.serviceInterface = serviceInterface;
        this.hostname = hostname;
        this.port = port;
        this.expireTime = expireTime;
    }

    public ServiceProvider() {
    }

    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "serviceInterface='" + serviceInterface + '\'' +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                ", expireTime=" + expireTime +
                '}';
    }
}
