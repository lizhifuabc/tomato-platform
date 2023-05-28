package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.PlaceholderResolver;
import com.tomato.mybatis.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 批量插入provider
 * @author lizhifu
 */
@Slf4j
public class BatchInsertSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param param  mybatis @Param注解绑定的param map
     * @param context context
     * @return  sql
     */
    public String sql(Map<String, Object> param, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), val -> {
            TableInfo table = tableInfo(context);
            @SuppressWarnings("unchecked")
            int size = ((List<Object>)param.get("entities")).size();
            // 构造 ( #{entities[1-->数组索引].fieldName}, #{entities[1].fieldName2})
            String value = "(" + String.join(",", Stream.of(table.fields)
                    .map(field -> "#{entities[${index}]." + field.getName() + "}").toArray(String[]::new)) + ")";
            String[] values = new String[size];
            Map<String, Object> fillIndex = new HashMap<>(2);
            for (int i = 0; i < size; i++) {
                fillIndex.put("index", i);
                values[i] = PlaceholderResolver.getDefaultResolver().resolveByMap(value, fillIndex);
            }
            SQL sql = new SQL()
                    .INSERT_INTO(table.tableName)
                    .INTO_COLUMNS(table.columns);
            String res = sql.toString() + " VALUES " + String.join(",", values);
            log.info("batch insert sql:\n{}",res);
            return res;
        });
    }

    /**
     * sql
     * @param params  mybatis @Param注解绑定的param map
     * @param context context
     * @return  sql
     */
    public String sqlSelective(Map<String, Object> params, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), val -> {
            Object entities = params.get("entities");
            List<Object> list = (List<Object>) entities;
            TableInfo table = tableInfo(context);
            Field[] notNullFields = Stream.of(table.fields)
                    .filter(field -> ReflectionUtils.getFieldValue(field, list.get(0)) != null && !table.primaryKeyColumn.equals(TableInfo.columnName(field)))
                    .toArray(Field[]::new);
            // 构造 ( #{entities[1-->数组索引].fieldName}, #{entities[1].fieldName2})
            String value = "(" + String.join(",", Stream.of(notNullFields)
                    .map(field -> "#{entities[${index}]." + field.getName() + "}").toArray(String[]::new)) + ")";
            String[] values = new String[list.size()];
            Map<String, Object> fillIndex = new HashMap<>(2);
            for (int i = 0; i < list.size(); i++) {
                fillIndex.put("index", i);
                values[i] = PlaceholderResolver.getDefaultResolver().resolveByMap(value, fillIndex);
            }
            SQL sql = new SQL()
                    .INSERT_INTO(table.tableName)
                    .INTO_COLUMNS(TableInfo.columns(notNullFields));
            String res = sql.toString() + " VALUES " + String.join(",", values);
            log.info("batch insert selective sql:\n{}",res);
            return res;
        });
    }
}