package rpc.config;

public class SystemCondition {
    /**
     * CPU 使用率
     */
    private Double systemCpuLoad;
    /**
     * 内存使用率
     */
    private Double memoryUseRatio;
    /**
     * 系统总内存
     */
    private Double totalMemory;
    /**
     * 系统剩余内存
     */
    private Double freeMemory;

    public SystemCondition(Double systemCpuLoad, Double memoryUseRatio, Double totalMemory, Double freeMemory) {
        this.systemCpuLoad = systemCpuLoad;
        this.memoryUseRatio = memoryUseRatio;
        this.totalMemory = totalMemory;
        this.freeMemory = freeMemory;
    }

    public Double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(Double systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    public Double getMemoryUseRatio() {
        return memoryUseRatio;
    }

    public void setMemoryUseRatio(Double memoryUseRatio) {
        this.memoryUseRatio = memoryUseRatio;
    }

    public Double getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Double totalMemory) {
        this.totalMemory = totalMemory;
    }

    public Double getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(Double freeMemory) {
        this.freeMemory = freeMemory;
    }

    @Override
    public String toString() {
        return "SystemCondition{" +
                "systemCpuLoad=" + systemCpuLoad +
                ", memoryUseRatio=" + memoryUseRatio +
                ", totalMemory=" + totalMemory +
                ", freeMemory=" + freeMemory +
                '}';
    }
}
