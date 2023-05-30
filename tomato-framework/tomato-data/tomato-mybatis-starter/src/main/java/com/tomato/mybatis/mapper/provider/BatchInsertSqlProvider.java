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
            TableInfo table = tableInfo(context);
            Field[] fields = table.fields;
            StringBuilder builder = new StringBuilder("<script>\n");
            builder.append(String.format("INSERT INTO \n%s %s VALUES", table.tableName,insertColumnsSql(table)));
            builder.append("\n<foreach collection=\"entities\" item=\"entity\" index=\"index\" separator=\",\">");
            builder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            for (Field field : fields) {
                builder.append("\n<if test=\"entity.").append(field.getName()).append(" != null\">");
                builder.append("\n").append("#{entity.").append(field.getName()).append("},");
                builder.append("\n</if>");
            }
            builder.append("\n</trim>");
            builder.append("\n</foreach>");
            builder.append("\n</script>");
            log.info("batch insert selective sql:\n{}",builder);
            return builder.toString();
        });
    }
}