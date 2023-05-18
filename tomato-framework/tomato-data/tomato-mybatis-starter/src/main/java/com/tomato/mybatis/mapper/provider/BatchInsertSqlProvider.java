package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.PlaceholderResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 批量插入provider
 * @author lizhifu
 */
@Slf4j
public class BatchInsertSqlProvider extends BaseSqlProviderSupport {
    /**
     * sql
     * @param param  mybatis @Param注解绑定的param map
     * @param context context
     * @return  sql
     */
    public String sql(Map<String, Object> param, ProviderContext context) {
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

        return SQL_CACHE.computeIfAbsent(getCacheKey(context), val -> {
            SQL sql = new SQL()
                    .INSERT_INTO(table.tableName)
                    .INTO_COLUMNS(table.columns);
            String res = sql.toString() + " VALUES " + String.join(",", values);
            log.info("batch insert sql:{}",res);
            return res;
        });
    }
}