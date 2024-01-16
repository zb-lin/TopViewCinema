package sspring.utils;


import sspring.bean.config.ClassDefinition;
import sspring.exception.BeanException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lorm.constant.ConfigurationConstant.DATABASE_CONFIGURATION_XML_PATH;

public class ClassScanner {

    /**
     * 测试环境下 出现 ConcurrentModificationException
     */
    private static final List<ClassDefinition> classDefinitionList = new CopyOnWriteArrayList<>();
    private static final int JAVA_SUFFIX_LENGTH = 5;
    private static final String JAVA = "java";
    private static final String PROTO = "proto";

    public ClassScanner() {
    }


    public List<ClassDefinition> getFileNames(String path, int projectNameLength) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            List<Path> filePaths = paths
                    .filter(Files::isRegularFile)
                    .filter(filePath -> JAVA.equals(filePath.toString().substring(filePath.toString().lastIndexOf('.') + 1)))
                    .collect(Collectors.toList());
            filePaths.forEach(filePath -> {
                // beanName:  包名 + 类名
                String beanName = getBeanName(filePath.toString(), projectNameLength);
                if (beanName.contains(PROTO)) {
                    return;
                }
                ClassDefinition classDefinition = new ClassDefinition();
                // 获取类名
                String simpleBeanName = beanName.substring(beanName.lastIndexOf('.') + 1);
                // 获取 bean 名 (将类名首字母小写)
                simpleBeanName = StringUtils.firstCharToLowerCase(simpleBeanName);
                classDefinition.setClassPath(beanName);
                classDefinition.setBeanName(simpleBeanName);
                classDefinitionList.add(classDefinition);
            });
            return classDefinitionList;
        } catch (IOException e) {
            throw new BeanException("包扫描异常", e);
        }
    }

    private String getBeanName(String fileName, int projectNameLength) {
        return fileName.substring(projectNameLength, fileName.length() - JAVA_SUFFIX_LENGTH).replace('\\', '.');
    }


}
