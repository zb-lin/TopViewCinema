package rpc.core.provider;


import lorm.factory.ConnectionFactory;
import lorm.factory.DefaultConnectionFactory;
import rpc.config.InvokeData;
import rpc.exception.RPCException;
import sspring.bean.factory.BeanFactory;
import sspring.bean.factory.BeanRegister;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static rpc.constants.Constants.*;

public class Invoker {
    private static class Instance {
        private static final Invoker INSTANCE = new Invoker();
    }

    private Invoker() {
    }

    public static Invoker getInstance() {
        return Instance.INSTANCE;
    }

    private static final ConnectionFactory factory = new DefaultConnectionFactory();
    private static final BeanRegister beanRegister = new BeanRegister(PATH, PROJECT_NAME_LENGTH);
    private static final BeanFactory beanFactory = beanRegister.getBeanFactory();


    public Object invoker(InvokeData invokeData) {
        Logger logger = Logger.getLogger(invokeData.getInterfaceName());
        Connection connection = factory.getConnection();
        Object result = null;
        try {
            connection.setAutoCommit(false);
            long begin = System.currentTimeMillis();

            // 反射调用中间解析层方法
            String interfaceName = invokeData.getInterfaceName();
            Object bean = beanFactory.getBean(MIDDLE + interfaceName);
            Method method = bean.getClass().getMethod(invokeData.getMethodName(), byte[].class);
            result = method.invoke(bean, invokeData.getRpcRequestByteArray());

            long end = System.currentTimeMillis();
            log(invokeData, logger, begin, end);
            connection.commit();
            return result;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            logger.warning("request_id: " + invokeData.getId() + " interfaceName: " + invokeData.getInterfaceName()
                    + " invoke method: " + invokeData.getMethodName() + "\n" + e);
            return null;
        } finally {
            factory.close();
        }
    }

    public void log(InvokeData invokeData, Logger logger, long begin, long end) {
        logger.log(Level.INFO, () -> " " + "request_id: " + invokeData.getId() + " interfaceName: "
                + invokeData.getInterfaceName() + " invoke method: " + invokeData.getMethodName()
                + " time: " + (end - begin) + "ms");
    }
}
