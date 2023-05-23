package com.tomato.mybatis.mapping;

import com.tomato.mybatis.annotation.NoColumn;
import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.mybatis.util.CollectionUtils;
import com.tomato.mybatis.util.ReflectionUtils;
import com.tomato.mybatis.util.StringUtils;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 表信息
 * @author lizhifu
 */
public class TableInfo {
    /**
     * 表前缀
     */
    private static final String TABLE_PREFIX = "t_";

    /**
     * 主键名
     */
    public static final String DEFAULT_PRIMARY_KEY = "id";

    /**
     * 表名
     */
    public String tableName;

    /**
     * 实体类型不含@NoColunm注解的field
     */
    public Field[] fields;

    /**
     * 主键列名
     */
    public String primaryKeyColumn;

    /**
     * 所有列名
     */
    public String[] columns;

    /**
     * 所有select sql的列名，有带下划线的将其转为aa_bb AS aaBb
     */
    public String[] selectColumns;

    private TableInfo() {}

    /**
     * 获取主键的where条件，如 id = #{id}
     *
     * @return  主键where条件
     */
    public String getPrimaryKeyWhere() {
        String pk = this.primaryKeyColumn;
        return pk + " = #{" + pk + "}";
    }

    /**
     * 获取主键的where条件，如 id = #{id}
     *
     * @return  主键where条件
     */
    public String getPrimaryKeyEntityWhere() {
        String pk = this.primaryKeyColumn;
        return pk + " = #{criteria." + pk + "}";
    }

    /**
     * 获取TableInfo的简单工厂
     *
     * @param mapperType mapper类型
     * @return            {@link TableInfo}
     */
    public static TableInfo of(Class<?> mapperType) {
        Class<?> entityClass = entityType(mapperType);
        // 获取不含有@NoColumn注解的fields
        Field[] fields = excludeNoColumnField(entityClass);
        TableInfo tableInfo = new TableInfo();
        tableInfo.fields = fields;
        tableInfo.tableName = tableName(entityClass);
        tableInfo.primaryKeyColumn =  primaryKeyColumn(fields);
        tableInfo.columns = columns(fields);
        tableInfo.selectColumns = selectColumns(fields);
        return tableInfo;
    }

    /**
     * 获取BaseMapper接口中的泛型类型
     *
     * @param mapperType  mapper类型
     * @return       实体类型
     */
    public static Class<?> entityType(Class<?> mapperType) {
        return Stream.of(mapperType.getGenericInterfaces())
                .filter(ParameterizedType.class::isInstance)
                .map(ParameterizedType.class::cast)
                .filter(type -> type.getRawType() == BaseMapper.class)
                .findFirst()
                .map(type -> type.getActualTypeArguments()[0])
                .filter(Class.class::isInstance).map(Class.class::cast)
                .orElseThrow(() -> new IllegalStateException("未找到BaseMapper的泛型类 " + mapperType.getName() + "."));
    }


    /**
     * 获取表名
     *
     * @param entityType  实体类型
     * @return      表名
     */
    public static String tableName(Class<?> entityType) {
        jakarta.persistence.Table table = entityType.getAnnotation(jakarta.persistence.Table.class);
        if (table == null || table.name().isEmpty()) {
            return TABLE_PREFIX + StringUtils.camel2Underscore(entityType.getSimpleName());
        }

        return Stream.of(table.catalog(), table.schema(), table.name())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining("."));
    }

    /**
     * 过滤含有@NoColumn注解或者是静态的field
     *
     * @param entityClass 实体类型
     * @return 不包含@NoColumn注解的fields
     */
    public static Field[] excludeNoColumnField(Class<?> entityClass) {
        Field[] allFields = ReflectionUtils.getFields(entityClass);
        List<String> excludeColumns = getClassExcludeColumns(entityClass);
        return Stream.of(allFields)
                //过滤掉类上指定的@NoCloumn注解的字段和字段上@NoColumn注解或者是静态的field
                .filter(field -> !CollectionUtils.contains(excludeColumns, field.getName())
                        && (!field.isAnnotationPresent(NoColumn.class) && !Modifier.isStatic(field.getModifiers())))
                .toArray(Field[]::new);
    }

    /**
     * 获取实体类上标注的不需要映射的字段名
     *
     * @param entityClass  实体类
     * @return             不需要映射的字段名
     */
    public static List<String> getClassExcludeColumns(Class<?> entityClass) {
        List<String> excludeColumns = null;
        NoColumn classNoColumns = entityClass.getAnnotation(NoColumn.class);
        if (classNoColumns != null) {
            excludeColumns = Arrays.asList(classNoColumns.fields());
        }
        return excludeColumns;
    }

    /**
     * 获取查询对应的字段 (不包含pojo中含有@NoColumn主键的属性)
     *
     * @param fields p
     * @return  所有需要查询的查询字段
     */
    public static String[] selectColumns(Field[] fields) {
        return Stream.of(fields).map(TableInfo::selectColumnName).toArray(String[]::new);
    }

    /**
     * 获取所有pojo所有属性对应的数据库字段 (不包含pojo中含有@NoColumn主键的属性)
     *
     * @param fields entityClass所有fields
     * @return        所有的column名称
     */
    public static String[] columns(Field[] fields) {
        return Stream.of(fields).map(TableInfo::columnName).toArray(String[]::new);
    }

    /**
     * 如果fields中含有@ID的字段，则返回该字段名为主键，否则默认'id'为主键名
     *
     * @param fields entityClass所有fields
     * @return 主键column(驼峰转为下划线)
     */
    public static String primaryKeyColumn(Field[] fields) {
        return Stream.of(fields).filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()    //返回第一个primaryKey的field
                .map(TableInfo::columnName)
                .orElse(DEFAULT_PRIMARY_KEY);
    }

    /**
     * 获取单个属性对应的数据库字段（带有下划线字段将其转换为"字段 AS pojo属性名"形式）
     *
     * @param field  字段
     * @return      带有下划线字段将其转换为"字段 AS pojo属性名"形式
     */
    public static String selectColumnName(Field field) {
        String camel = columnName(field);
        return camel.contains("_") ? camel + " AS `" + field.getName() + "`" : camel;
    }

    /**
     * 获取单个属性对应的数据库字段
     *
     * @param field entityClass中的field
     * @return  字段对应的column
     */
    public static String columnName(Field field) {
        return "`" + StringUtils.camel2Underscore(field.getName()) + "`";
    }

    /**
     * 绑定参数
     *
     * @param field  字段
     * @return        参数格式
     */
    public static String bindParameter(Field field) {
        return "#{criteria." + field.getName() + "}";
    }

    /**
     * 获取该字段的参数赋值语句，如 user_name = #{userName}
     * @param field  字段
     * @return       参数赋值语句
     */
    public static String assignParameter(Field field) {
        return columnName(field) + " = " + bindParameter(field);
    }
}