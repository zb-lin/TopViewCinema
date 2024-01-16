package lorm.factory.query;


import lorm.aop.AbstractHandler;
import lorm.aop.LoggerHandler;
import lorm.aop.ProxyFactory;
import lorm.factory.xml.XmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建Query对象的工厂类
 *
 * @author lzb
 */
public class QueryFactory {
    private QueryFactory() {
    }

    private static QueryImpl query;
    private static final XmlParse xmlParse = new XmlParse();


    static {
        try {
            Class<?> queryClass = Class.forName(xmlParse.getConfiguration().getQueryClass());
            query = (QueryImpl) queryClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Query createQuery() {
        LoggerHandler loggerHandler = new LoggerHandler(query);
        List<AbstractHandler> handlers = new ArrayList<>();
        handlers.add(loggerHandler);
        return (Query) ProxyFactory.getProxy(query, handlers);
    }
}
