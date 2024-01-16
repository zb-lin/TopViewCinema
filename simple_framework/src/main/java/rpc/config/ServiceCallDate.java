package rpc.config;

public class ServiceCallDate {
    /**
     * 服务名
     */
    private String interfaceName;
    /**
     * 服务调用的次数
     */
    private Integer callCount;
    /**
     * 成功率 100% ...
     */
    private Integer successRate;
    /**
     * 平均响应时间
     */
    private Integer timeout;

    public ServiceCallDate(String interfaceName, Integer callCount, Integer successRate, Integer timeout) {
        this.interfaceName = interfaceName;
        this.callCount = callCount;
        this.successRate = successRate;
        this.timeout = timeout;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public Integer getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Integer successRate) {
        this.successRate = successRate;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }


}
