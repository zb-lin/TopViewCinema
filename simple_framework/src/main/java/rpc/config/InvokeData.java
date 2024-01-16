package rpc.config;

/**
 * Serializable 可序列化
 */
public class InvokeData {
    /**
     * 请求id
     */
    private String id;
    /**
     * 请求接口名
     */
    private String interfaceName;
    /**
     * 请求方法名
     */
    private String methodName;
    /**
     * 参数 byte[] protobuf序列化过
     */
    private Object rpcRequestByteArray;

    public InvokeData(String id, String interfaceName, String methodName, Object rpcRequestByteArray) {
        this.id = id;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.rpcRequestByteArray = rpcRequestByteArray;
    }

    public InvokeData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getRpcRequestByteArray() {
        return rpcRequestByteArray;
    }

    public void setRpcRequestByteArray(Object rpcRequestByteArray) {
        this.rpcRequestByteArray = rpcRequestByteArray;
    }

    @Override
    public String toString() {
        return "InvokeData{" +
                "id='" + id + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", rpcRequestByteArray=" + rpcRequestByteArray +
                '}';
    }
}
