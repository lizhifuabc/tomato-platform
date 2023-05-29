package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;

/**
 * 根据条件统计
 * @author lizhifu
 */
@Slf4j
public class CountByCriteriaSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param params  params 条件
     * @param context context
     * @return  sql
     */
    public String sql(Map<String, Object> params, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            TableInfo table = tableInfo(context);
            StringBuilder builder = new StringBuilder("<script>\n");
            builder.append(String.format("select COUNT(*) from \n%s", table.tableName));
            builder.append(buildWhereNotNullXML(table));
            builder.append("\n</script>");
            log.info("count criteria sql:\n{}",builder);
            return builder.toString();
        });
    }
}
