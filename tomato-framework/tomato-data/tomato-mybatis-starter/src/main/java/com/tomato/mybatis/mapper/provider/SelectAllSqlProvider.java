package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

/**
 * 查询所有记录
 * @author lizhifu
 */
@Slf4j
public class SelectAllSqlProvider extends BaseSqlProviderSupport {
    /**
     * sql
     * @param orderBy  排序字段
     * @param context context
     * @return  sql
     */
    public String sql(String orderBy, ProviderContext context) {
        TableInfo table = tableInfo(context);
        SQL sql = new SQL()
                .SELECT(table.selectColumns)
                .FROM(table.tableName);
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = table.primaryKeyColumn + " DESC";
        }
        String res = sql.ORDER_BY(orderBy).toString();

        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            log.info("select all sql:\n{}",res);
            return res;
        });

    }
}
