package sspring.bean.factory;


import rpc.core.consumer.ProxyFactory;
import sspring.bean.config.*;
import sspring.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private static final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private static final Map<String, Object> singletonObjects = new HashMap<>();

    public void getBean() {
        for (String beanName : beanDefinitionMap.keySet()) {
            getBean(beanName, beanDefinitionMap.get(beanName));
        }
    }

    public Object getBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = singletonObjects.get(beanName);
        if (bean != null) {
            return bean;
        }
        try {
            Class<?> beanClass = beanDefinitionMap.get(beanName).getBeanClass();
            bean = beanClass.getDeclaredConstructor().newInstance();
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String fieldName = propertyValue.getName();
                Object value = propertyValue.getValue();
                Field field = beanClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                // 普通注入
                if (value instanceof BeanReference) {
                    // 有依赖循环问题
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName(), beanDefinitionMap.get(beanReference.getBeanName()));
                    field.set(bean, value);
                    continue;
                }
                // rpc远程调用代理对象
                if (value instanceof BeanRpcProxy) {
                    // 有依赖循环问题
                    Class<?> type = field.getType();
                    value = ProxyFactory.getProxy(type);
                    field.set(bean, value);
                    continue;
                }
                // 日志记录代理对象
                if (value instanceof BeanLogging) {
                    // 有依赖循环问题
                    BeanLogging beanLogging = (BeanLogging) value;
                    Class<?> type = field.getType();
                    value = getBean(beanLogging.getBeanName(), beanDefinitionMap.get(beanLogging.getBeanName()));
                    value = ProxyFactory.getLoggingProxy(type, value);
                    field.set(bean, value);
                    continue;
                }
                // Mock
                if (value instanceof BeanMock) {
                    // 有依赖循环问题
                    BeanMock beanMock = (BeanMock) value;
                    value = getBean(beanMock.getBeanName(), beanDefinitionMap.get(beanMock.getBeanName()));
                    field.set(bean, value);
                }
            }
            singletonObjects.put(beanName, bean);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T getBean(Class<?> beanClass) {
        return (T) getBean(StringUtils.firstCharToLowerCase(beanClass.getSimpleName()), null);
    }

    public <T> T getBean(String interfaceName) {
        return (T) getBean(StringUtils.firstCharToLowerCase(interfaceName), null);
    }

    public boolean isContainsBean(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    public void registerBean(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    public static void removeBean(String beanName) {
        singletonObjects.remove(beanName);
    }

    public static void removeBean(Class<?> beanClass) {
        singletonObjects.remove(StringUtils.firstCharToLowerCase(beanClass.getSimpleName()));
    }
}
