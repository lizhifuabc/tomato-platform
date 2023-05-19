package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.paginate.Page;
import com.tomato.mybatis.util.ReflectionUtils;
import com.tomato.mybatis.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;
import java.util.stream.Stream;

/**
 * 根据条件查询
 * @author lizhifu
 */
@Slf4j
public class SelectPageByCriteriaSqlProvider extends BaseSqlProviderSupport {
    /**
     * sql
     * @param params  param 条件
     * @param context context
     * @return  sql
     */
    public String sql(Map<String,Object> params, ProviderContext context) {
        Object criteria = params.get("criteria");
        String orderBy = (String) params.get("orderBy");
        Page page = (Page) params.get("page");
        TableInfo table = tableInfo(context);
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = table.primaryKeyColumn + " DESC";
        }
        final String finalOrderBy = orderBy;
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            SQL sql = new SQL()
                    .SELECT(table.selectColumns)
                    .FROM(table.tableName)
                    .WHERE(Stream.of(table.fields)
                            .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                            .map(TableInfo::assignParameter)
                            .toArray(String[]::new)
                    )
                    .ORDER_BY(finalOrderBy)
                    .getSelf().OFFSET(page.getOffset()).LIMIT(page.getLimit());
            log.info("select page criteria sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}