package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

/**
 * 统计所有数据
 * @author lizhifu
 */
@Slf4j
public class CountSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param context context
     * @return  sql
     */
    public String sql(ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            TableInfo table = tableInfo(context);
            SQL sql = new SQL()
                    .SELECT("COUNT(*)")
                    .FROM(table.tableName);
            log.info("count sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}