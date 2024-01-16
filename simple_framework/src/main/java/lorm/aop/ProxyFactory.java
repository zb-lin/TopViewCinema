package lorm.aop;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 代理工厂类
 */
public class ProxyFactory {
    private ProxyFactory() {
    }

    public static Object getProxy(Object targetObject, List<AbstractHandler> handlers) {
        Object proxyObject;
        if (!handlers.isEmpty()) {
            proxyObject = targetObject;
            for (AbstractHandler handler : handlers) {
                proxyObject = Proxy.newProxyInstance(targetObject.getClass()
                        .getClassLoader(), targetObject.getClass()
                        .getInterfaces(), handler);
            }
            return proxyObject;
        } else {
            return targetObject;
        }
    }
}
