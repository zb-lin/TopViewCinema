package rpc.config;

import java.io.Serializable;

public class Host implements Serializable {
    /**
     * ip地址
     */
    private String hostname;
    /**
     * 端口号
     */
    private Integer port;

    public Host(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
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

    @Override
    public String toString() {
        return "Host{" +
                "hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }
}
