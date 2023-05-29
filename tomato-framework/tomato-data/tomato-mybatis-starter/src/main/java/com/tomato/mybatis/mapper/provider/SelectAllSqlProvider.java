package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.domain.Sort;
import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

/**
 * 查询所有记录
 * @author lizhifu
 */
@Slf4j
public class SelectAllSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param sort  排序字段
     * @param context context
     * @return  sql
     */
    public String sql(Sort sort, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            TableInfo table = tableInfo(context);
            SQL sql = new SQL()
                    .SELECT(table.selectColumns)
                    .FROM(table.tableName);
            String res = sql.ORDER_BY(orderBySqlSimple(sort)).toString();
            log.info("select all sql:\n{}",res);
            return res;
        });

    }
}
