package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.domain.Sort;
import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.paginate.Page;
import com.tomato.mybatis.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;
import java.util.stream.Stream;


/**
 * 分页查询
 * @author lizhifu
 */
@Slf4j
public class SelectPageByCriteriaSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param params  param 条件
     * @param context context
     * @return  sql
     */
    public String sql(Map<String,Object> params, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            Object criteria = params.get("criteria");
            Page page = (Page) params.get("page");
            TableInfo table = tableInfo(context);
            Sort sort = page.getSort();
            SQL sql = new SQL()
                    .SELECT(table.selectColumns)
                    .FROM(table.tableName)
                    .WHERE(Stream.of(table.fields)
                            .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                            .map(TableInfo::assignParameter)
                            .toArray(String[]::new)
                    )
                    .ORDER_BY(orderBySqlSimple(sort))
                    .getSelf().OFFSET(page.getOffset()).LIMIT(page.getLimit());
            log.info("select page criteria sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}