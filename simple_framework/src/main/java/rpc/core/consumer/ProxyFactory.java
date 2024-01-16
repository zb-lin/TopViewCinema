package rpc.core.consumer;


import lorm.aop.LoggerHandler;

import java.lang.reflect.Proxy;


@SuppressWarnings("all")
public class ProxyFactory<T> {

    public static <T> T getProxy(Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                new RpcHandler(interfaceClass));
    }

    public static <T> T getLoggingProxy(Class<?> interfaceClass, Object bean) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                new LoggerHandler(bean));
    }
}
