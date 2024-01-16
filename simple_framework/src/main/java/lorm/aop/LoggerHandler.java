package lorm.aop;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 通过动态代理记录日志
 *
 * @author lzb
 */
public class LoggerHandler implements AbstractHandler {

    private final Object target;

    public LoggerHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger logger = Logger.getLogger(target.getClass().getSimpleName());
        // 打印日志到控制台
        Object result = null;
        try {
            long begin = System.currentTimeMillis();
            result = method.invoke(target, args);
            long end = System.currentTimeMillis();
            logger.info("method: " + method.getName() + "; args: " +
                    Arrays.asList(args) + "; result: " + JSON.toJSONString(result) +
                    "; time: " + (end - begin) + "ms");
        } catch (Exception e) {
            logger.warning("invoke method: " + method.getName()
                    + "; args: " + (args == null ? "null" : Arrays.asList(args).toString()) + "\n" + e);
        }
        return result;
    }

}
