package lorm.utils;

import lorm.annotation.ColumnName;
import lorm.annotation.TableName;

import java.lang.reflect.Field;

public class Table {

    /**
     * 获得实体类对应的表名
     */
    public static String getTableName(Class<?> poClass) {
        // 获得类的指定注解
        return poClass.getAnnotation(TableName.class).value();
    }

    /**
     * 获取实体类属性对应表的列名
     *
     * @param field 属性
     * @return 列名
     */
    public static String getColumnName(Field field) {
        ColumnName annotation = field.getAnnotation(ColumnName.class);
        if (annotation == null) return field.getName();
        return annotation.value();
    }

    /**
     * 获取实体类属性对应表的列名
     *
     * @param fieldName 属性名
     * @param obj       实体类
     * @return 列名
     */
    public static String getColumnName(String fieldName, Object obj) {
        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            return getColumnName(field);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获得列名对应的属性名
     *
     * @param columnName 列名
     * @param objClass   对象
     * @return 属性名
     */
    public static String getFieldName(String columnName, Class<?> objClass) {
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            if (Table.getColumnName(field).equals(columnName)) {
                return field.getName();
            }
        }
        return null;
    }

}
