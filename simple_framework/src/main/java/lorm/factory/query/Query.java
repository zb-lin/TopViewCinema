package lorm.factory.query;

import java.util.List;

/**
 * 负责查询(对外提供服务的核心类)
 *
 * @author lzb
 */
public interface Query {
    /**
     * 将一个对象存储到数据库中
     * 把数据库中不为null的部分往数据库中存储
     * 如果数字为null, 则放0
     *
     * @param obj 要储存的对象
     */
    boolean insert(Object obj);

    /**
     * 删除class表示类对应的表中的记录(指定主键值id的记录)
     *
     * @param thisClass 跟表对应的类的class对象
     * @param id        键的值
     */
    boolean delete(Class<?> thisClass, Object id);

    /**
     * 删除对象在数据库中对应的记录(对象所在的类对应到表, 对象的主键的值对应到记录)
     *
     * @param obj 要删除的对象
     */
    boolean delete(Object obj);

    /**
     * 更新对象对应的记录, 并且只更新指定的字段的值
     *
     * @param obj        所要更新的对象
     * @param fieldNames 更新的属性列表
     * @return 执行sql语句后影响记录的行数
     */
    boolean update(Object obj, String[] fieldNames);

    /**
     * 自定义更新
     *
     * @param sql    sql语句
     * @param params 参数
     */
    boolean update(String sql, Object[] params);

    /**
     * 查询返回多行记录, 并将每行记录封装到clazz指定的类的对象中
     *
     * @param sql       查询语句
     * @param thisClass 封装数据的javabean类的Class对象
     * @param params    sql的参数
     * @return 查询的结果
     */
    List<Object> queryRows(final String sql, final Class<?> thisClass, final Object[] params);

    /**
     * 查询返回一行记录, 并将该记录封装到clazz指定的类的对象中
     *
     * @param sql       查询语句
     * @param thisClass 封装数据的javabean类的Class对象
     * @param params    sql的参数
     * @return 查询的结果
     */
    Object queryUniqueRow(String sql, Class<?> thisClass, Object[] params);

    /**
     * 根据主键查找对象
     *
     * @param thisClass 封装数据的javabean类的Class对象
     * @param id        主键
     * @return 查询到的对象
     */
    Object queryById(Class<?> thisClass, Object id);

    /**
     * 查询返回一个值(一行一列), 并将该值返回
     *
     * @param sql    查询语句
     * @param params sql的参数
     * @return 查询的结果
     */
    Object queryValue(String sql, Object[] params);

    /**
     * 查询返回一个数字(一行一列), 并将该值返回
     *
     * @param sql    查询语句
     * @param params sql的参数
     * @return 查询的数字
     */
    Number queryNumber(String sql, Object[] params);

    /**
     * 分页查询
     *
     * @param begin 第几页数据
     * @param size  每页显示多少记录
     */
    List<Object> queryPage(Class<?> objClass, int begin, int size);

}
