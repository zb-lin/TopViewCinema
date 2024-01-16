package sspring.bean.config;

public class ClassDefinition {
    private String beanName;
    private String classPath;

    public ClassDefinition(String beanName, String classPath) {
        this.beanName = beanName;
        this.classPath = classPath;
    }

    public ClassDefinition() {
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    @Override
    public String toString() {
        return "ClassDefinition{" +
                "beanName='" + beanName + '\'' +
                ", classPath='" + classPath + '\'' +
                '}';
    }
}
