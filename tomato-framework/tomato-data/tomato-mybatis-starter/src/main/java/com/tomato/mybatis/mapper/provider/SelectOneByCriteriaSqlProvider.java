package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;

/**
 * 根据条件查询
 * @author lizhifu
 */
@Slf4j
public class SelectOneByCriteriaSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param params  params 条件
     * @param context context
     * @return  sql
     */
    public String sql(Map<String,Object> params, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            TableInfo table = tableInfo(context);
            StringBuilder builder = new StringBuilder("<script>\n");
            builder.append(String.format("select \n%s \n from \n%s", String.join(",", table.selectColumns), table.tableName));
            builder.append(whereSql(table));
            builder.append("\n order by ").append(table.primaryKeyColumn).append(" DESC");
            builder.append("\n</script>");
            log.info("selectOneByCriteria sql:\n{}",builder);
            return builder.toString();
        });
    }
}