package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.stream.Stream;

/**
 * 根据条件查询
 * @author lizhifu
 */
@Slf4j
public class SelectByCriteriaSqlProvider extends BaseSqlProviderSupport {
    /**
     * sql
     * @param criteria  entity 条件
     * @param context context
     * @return  sql
     */
    public String sql(Object criteria, ProviderContext context) {
        TableInfo table = tableInfo(context);

        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            SQL sql = new SQL()
                    .SELECT(table.selectColumns)
                    .FROM(table.tableName)
                    .WHERE(Stream.of(table.fields)
                            .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                            .map(TableInfo::assignParameter)
                            .toArray(String[]::new)).ORDER_BY(table.primaryKeyColumn + " DESC");
            log.info("selectByCriteria sql:\n{}",sql.toString());
            return sql.toString();
        });


    }
}