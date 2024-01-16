package lorm.factory.query;


import lorm.utils.ReflectUtils;
import lorm.utils.Table;

import java.lang.reflect.Field;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责查询(对外提供服务的核心类)
 *
 * @author lzb
 */
public class QueryImpl extends QueryTemplate implements Query {

    /**
     * 将一个对象存储到数据库中
     * 把数据库中不为null的部分往数据库中存储
     * 如果数字为null, 则放0
     *
     * @param obj 要储存的对象
     */
    public boolean insert(Object obj) {
        // insert into tb_user(username,password,gender,camp) values (?,?,?,?)
        Class<?> objClass = obj.getClass();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("insert into ");
        sql.append(Table.getTableName(objClass)).append(" (");
        // 计算不为null的属性值(及后面拼接的 ? 的个数)
        int countNotNullField = 0;
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = ReflectUtils.invokeGet(fieldName, obj);
            fieldName = Table.getColumnName(field);
            if (fieldValue != null) {
                countNotNullField++;
                sql.append(fieldName).append(",");
                params.add(fieldValue);
            }
        }
        sql.setCharAt(sql.length() - 1, ')');
        sql.append(" values (");
        for (int i = 0; i < countNotNullField; i++) {
            sql.append("?,");
        }
        sql.setCharAt(sql.length() - 1, ')');
        return executeDML(sql.toString(), params.toArray());
    }

    /**
     * 删除class表示类对应的表中的记录(指定主键值id的记录)
     *
     * @param thisClass 跟表对应的类的class对象
     * @param id        键的值
     */
    public boolean delete(Class<?> thisClass, Object id) {
        String sql = "delete from " + Table.getTableName(thisClass) + " where id=? ";
        return executeDML(sql, new Object[]{id});
    }

    /**
     * 删除对象在数据库中对应的记录(对象所在的类对应到表, 对象的主键的值对应到记录)
     *
     * @param obj 要删除的对象
     */
    public boolean delete(Object obj) {
        Class<?> objClass = obj.getClass();

        // 通过反射机制, 调用属性对应的get方法或set方法
        Object priKeyValue = ReflectUtils.invokeGet("id", obj);
        return delete(objClass, priKeyValue);
    }

    /**
     * 更新对象对应的记录, 并且只更新指定的字段的值
     *
     * @param obj        所要更新的对象
     * @param fieldNames 更新的属性列表
     * @return 执行sql语句后影响记录的行数
     */
    public boolean update(Object obj, String[] fieldNames) {
        // update 表名  set uname=?,pwd=? where id=?
        Class<?> objClass = obj.getClass();
        // 存储sql的参数对象
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("update ");
        sql.append(Table.getTableName(objClass)).append(" set ");
        for (String fieldName : fieldNames) {
            Object fieldValue = ReflectUtils.invokeGet(fieldName, obj);
            params.add(fieldValue);
            sql.append(Table.getColumnName(fieldName, obj)).append("=?,");
        }
        sql.setCharAt(sql.length() - 1, ' ');
        sql.append(" where id=?");
        // 主键的值
        params.add(ReflectUtils.invokeGet("id", obj));
        return executeDML(sql.toString(), params.toArray());
    }

    /**
     * 自定义更新
     *
     * @param sql    sql语句
     * @param params 参数
     */
    public boolean update(String sql, Object[] params) {
        return executeDML(sql, params);
    }


    /**
     * 查询返回多行记录, 并将每行记录封装到clazz指定的类的对象中
     *
     * @param sql       查询语句
     * @param thisClass 封装数据的javabean类的Class对象
     * @param params    sql的参数
     * @return 查询的结果
     */
    public List queryRows(String sql, Class<?> thisClass, Object[] params) {
        return (List) executeQueryTemplate(sql, params, (connection, preparedStatement, resultSet) -> {
            ResultSetMetaData metaData = null;
            List<Object> objectList = null;
            try {
                metaData = resultSet.getMetaData();
                // 多行
                while (resultSet.next()) {
                    if (objectList == null) {
                        objectList = new ArrayList<>();
                    }
                    // 调用javabean的无参构造器
                    Object rowObj = thisClass.newInstance();
                    //多列       select username ,pwd,age from user where id>? and age>18
                    for (int i = 0; i < metaData.getColumnCount(); i++) {
                        Object columnValue = resultSet.getObject(i + 1);
                        String columnName = Table.getFieldName(metaData.getColumnLabel(i + 1), thisClass);
                        ReflectUtils.invokeSet(rowObj, columnName, columnValue);
                    }
                    objectList.add(rowObj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return objectList;
        });

    }

    /**
     * 查询返回一行记录, 并将该记录封装到clazz指定的类的对象中
     *
     * @param sql       查询语句
     * @param thisClass 封装数据的javabean类的Class对象
     * @param params    sql的参数
     * @return 查询的结果
     */
    public Object queryUniqueRow(String sql, Class<?> thisClass, Object[] params) {
        List<Object> list = queryRows(sql, thisClass, params);
        return (list != null && list.size() > 0) ? list.get(0) : null;
    }

    /**
     * 根据主键查找对象
     *
     * @param thisClass 封装数据的javabean类的Class对象
     * @param id        主键
     * @return 查询到的对象
     */
    public Object queryById(Class<?> thisClass, Object id) {
        String sql = "select * from " + Table.getTableName(thisClass) + " where id=? ";
        return queryUniqueRow(sql, thisClass, new Object[]{id});
    }


    /**
     * 查询返回一个值(一行一列), 并将该值返回
     *
     * @param sql    查询语句
     * @param params sql的参数
     * @return 查询的结果
     */
    public Object queryValue(String sql, Object[] params) {
        return executeQueryTemplate(sql, params, (connection, preparedStatement, resultSet) -> {
            Object value = null;
            try {
                while (resultSet.next()) {
                    value = resultSet.getObject(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return value;
        });
    }

    /**
     * 查询返回一个数字(一行一列), 并将该值返回
     *
     * @param sql    查询语句
     * @param params sql的参数
     * @return 查询的数字
     */
    public Number queryNumber(String sql, Object[] params) {
        return (Number) queryValue(sql, params);
    }

    /**
     * 分页查询
     *
     * @param begin 第几页数据
     * @param size  每页显示多少记录
     * @return 查询的数据
     */
    public List<Object> queryPage(Class<?> objClass, int begin, int size) {
        String sql = "select * from " + Table.getTableName(objClass) + " limit ?, ?";
        return queryRows(sql, objClass, new Object[]{begin, size});
    }

}
