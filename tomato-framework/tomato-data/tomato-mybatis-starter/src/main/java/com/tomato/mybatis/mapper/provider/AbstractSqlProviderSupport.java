package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.domain.Sort;
import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * sql provider 抽象类
 *
 * @author lizhifu
 * @since 2023/5/18
 */
@Slf4j
public abstract class AbstractSqlProviderSupport {
    /**
     * key -> mapper class   value -> tableInfo
     */
    private static final Map<Class<?>, TableInfo> TABLE_CACHE = new ConcurrentHashMap<>(128);
    /**
     * key -> {@link #getCacheKey}   value -> SQL
     */
    static final Map<String, String> SQL_CACHE = new ConcurrentHashMap<>(128);
    /**
     * 获取表信息结构
     *
     * @param context  provider context
     * @return  表基本信息
     */
    protected TableInfo tableInfo(ProviderContext context) {
        log.info("context:{}",context.getMapperType());
        // 如果不存在则创建
        return TABLE_CACHE.computeIfAbsent(context.getMapperType(), TableInfo::of);
    }
    static String getCacheKey(ProviderContext context) {
        return context.getMapperType().getSimpleName() + ":" + context.getMapperMethod().getName();
    }

    protected String orderBySql(Sort sort) {
        return sort.getOrders().stream().map(order -> order.column() + " " + order.direction()).collect(Collectors.joining(","));
    }
    /**
     * 构建WHERE条件不能为null的SQL语句XML片段
     *
     * @param table 实体类映射
     * @return SQL片段
     */
    protected String buildWhereNotNullXML(TableInfo table) {
        Field[] fields = table.fields;
        String[] columns = table.columns;
        StringBuilder builder = new StringBuilder();
        builder.append("\n<where>");
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            Field field = fields[i];
            builder.append(String.format("\n<if test='criteria.%s != null'> AND %s = #{criteria.%s}</if>", field.getName(), column, field.getName()));
        }
        builder.append("\n</where>");
        return builder.toString();
    }
}
