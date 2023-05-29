package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 插入非空字段
 * @author lizhifu
 */
@Slf4j
public class InsertSelectiveSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param params  params 条件
     * @param context context
     * @return  sql
     */
    public String sql(Map<String, Object> params, ProviderContext context) {
        // 缓存需要添加size,此时存在问题，如果第一次插入的是2条数据，第二次插入的是3条数据，那么第二次插入的sql会被缓存
        // 如果数据比较多，那么缓存的sql会很多，需要考虑缓存的问题
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            TableInfo table = tableInfo(context);
            String sql = insertSelective(table);
            log.info("insert selective sql:\n{}",sql);
            return sql;
        });
    }
    /**
     * 插入非null的记录
     *
     * @param table 实体类映射
     * @return SQL语句
     */
    private String insertSelective(TableInfo table) {
        StringBuilder builder = new StringBuilder("<script>\n");
        StringBuilder columnsBuilder = new StringBuilder("\n<trim prefix='(' suffix=')' suffixOverrides=','>");
        StringBuilder valuesBuilder = new StringBuilder("\n<trim prefix='(' suffix=')' suffixOverrides=','>");
        Field[] fields = table.fields;
        String[] columns = table.columns;
        for (int i = 0; i < columns.length; i++) {
            String column = columns[i];
            Field field = fields[i];
            columnsBuilder.append(String.format("\n<if test='criteria.%s != null'>%s,</if>", field.getName(), column));
            valuesBuilder.append(String.format("\n<if test='criteria.%s != null'>#{criteria.%s},</if>", field.getName(), field.getName()));
        }
        columnsBuilder.append("\n</trim>");
        valuesBuilder.append("\n</trim>");
        builder.append(String.format("INSERT INTO \n%s %s VALUES%s", table.tableName, columnsBuilder, valuesBuilder));
        builder.append("\n</script>");
        return builder.toString();
    }
}