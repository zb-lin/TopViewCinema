package sspring.bean.config;

public class BeanMock {
    private final String beanName;

    public BeanMock(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
