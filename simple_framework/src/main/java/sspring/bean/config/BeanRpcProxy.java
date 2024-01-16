package sspring.bean.config;

/**
 * rpc远程调用 注入标记
 */
public class BeanRpcProxy {
    private final String beanName;

    public BeanRpcProxy(String beanName) {
        this.beanName = beanName;
    }


    public String getBeanName() {
        return beanName;
    }


}
