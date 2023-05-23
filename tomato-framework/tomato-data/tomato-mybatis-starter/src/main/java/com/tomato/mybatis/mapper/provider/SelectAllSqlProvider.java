package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.domain.Sort;
import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.stream.Collectors;

/**
 * 查询所有记录
 * @author lizhifu
 */
@Slf4j
public class SelectAllSqlProvider extends BaseSqlProviderSupport {
    /**
     * sql
     * @param sort  排序字段
     * @param context context
     * @return  sql
     */
    public String sql(Sort sort, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            TableInfo table = tableInfo(context);
            String finalOrderBy = sort.getOrders().stream().map(order -> order.column() + " " + order.direction()).collect(Collectors.joining(","));
            SQL sql = new SQL()
                    .SELECT(table.selectColumns)
                    .FROM(table.tableName);
            String res = sql.ORDER_BY(finalOrderBy).toString();
            log.info("select all sql:\n{}",res);
            return res;
        });

    }
}
