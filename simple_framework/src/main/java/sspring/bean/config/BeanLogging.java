package sspring.bean.config;

/**
 * 注入日志代理对象标记
 */
public class BeanLogging {
    private final String beanName;

    public BeanLogging(String beanName) {
        this.beanName = beanName;
    }


    public String getBeanName() {
        return beanName;
    }
}
