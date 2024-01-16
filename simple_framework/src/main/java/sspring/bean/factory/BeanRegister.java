package sspring.bean.factory;


import sspring.bean.annotation.*;
import sspring.bean.config.*;
import sspring.exception.BeanException;
import sspring.utils.ClassScanner;
import sspring.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BeanRegister {
    private final String path;
    private final int projectNameLength;
    private static final List<ClassDefinition> importClassList = new CopyOnWriteArrayList<>();

    public BeanRegister(String path, int projectNameLength) {
        this.path = path;
        this.projectNameLength = projectNameLength;
        doLoadBeanDefinitions();
    }

    private final BeanFactory beanFactory = new BeanFactory();

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    protected void doLoadBeanDefinitions() {
        ClassScanner classScanner = new ClassScanner();
        List<ClassDefinition> fileNames = classScanner.getFileNames(path, projectNameLength);
        registerBeanDefinitions(fileNames);
    }

    private void registerBeanDefinitions(List<ClassDefinition> fileNames) {
        for (ClassDefinition classDefinition : fileNames) {
            Class<?> beanClass = null;
            try {
                beanClass = Class.forName(classDefinition.getClassPath());
            } catch (ClassNotFoundException e) {
                throw new BeanException("Class Not Found: " + classDefinition.getClassPath(), e);
            }
            Hosted hosted = beanClass.getAnnotation(Hosted.class);
            if (hosted == null) {
                continue;
            }
            // 手动导入
            Import anImport = beanClass.getAnnotation(Import.class);
            if (anImport != null) {
                String beanName = anImport.beanName();
                if ("".equals(beanName)) {
                    int index = anImport.packageName().lastIndexOf(".");
                    String className = anImport.packageName().substring(index + 1);
                    beanName = StringUtils.firstCharToLowerCase(className);
                }
                importClassList.add(new ClassDefinition(beanName, anImport.packageName()));
            }
            registerBean(beanClass, classDefinition);
        }
        // 继续导入手动导入的bean
        for (ClassDefinition classDefinition : importClassList) {
            Class<?> beanClass = null;
            try {
                beanClass = Class.forName(classDefinition.getClassPath());
            } catch (ClassNotFoundException e) {
                throw new BeanException("Class Not Found: " + classDefinition.getClassPath(), e);
            }
            registerBean(beanClass, classDefinition);
        }
        beanFactory.getBean();
    }

    private void registerBean(Class<?> beanClass, ClassDefinition classDefinition) {
        String beanName = classDefinition.getBeanName();
        if (beanClass.getInterfaces().length > 0) {
            beanName = StringUtils.firstCharToLowerCase(beanClass.getInterfaces()[0].getSimpleName());
        }
        if (beanFactory.isContainsBean(beanName)) {
            return;
        }
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);
        Field[] fields = beanClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            String fieldName = field.getName();
            // 普通注入
            if (field.isAnnotationPresent(Autowired.class)) {
                Object value = new BeanReference(fieldName);
                PropertyValue propertyValue = new PropertyValue(fieldName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            // rpc远程调用代理对象
            if (field.isAnnotationPresent(RpcProxy.class)) {
                Object value = new BeanRpcProxy(fieldName);
                PropertyValue propertyValue = new PropertyValue(fieldName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            // 日志记录代理对象
            if (field.isAnnotationPresent(LoggerProxy.class)) {
                Object value = new BeanLogging(fieldName);
                PropertyValue propertyValue = new PropertyValue(fieldName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (field.isAnnotationPresent(MockBean.class)) {
                Object value = new BeanMock(fieldName);
                PropertyValue propertyValue = new PropertyValue(fieldName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
        });
        // 注册 BeanDefinition
        beanFactory.registerBean(beanName, beanDefinition);
    }


}
