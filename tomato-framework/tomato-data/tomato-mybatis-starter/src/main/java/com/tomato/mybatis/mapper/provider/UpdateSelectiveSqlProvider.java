package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;
import java.util.stream.Stream;

/**
 * 只能新非空字段 provider
 * @author lizhifu
 */
@Slf4j
public class UpdateSelectiveSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param params  params
     * @param context context
     * @return  sql
     */
    public String sql(Map<String, Object> params, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            TableInfo table = tableInfo(context);
            Object criteria = params.get("criteria");
            SQL sql = new SQL()
                    .UPDATE(table.tableName)
                    .SET(Stream.of(table.fields)
                            .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null && !table.primaryKeyColumn.equals(TableInfo.columnName(field)))
                            .map(TableInfo::assignParameter).toArray(String[]::new))
                    .WHERE(table.getPrimaryKeyEntityWhere());
            log.info("update selective sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}