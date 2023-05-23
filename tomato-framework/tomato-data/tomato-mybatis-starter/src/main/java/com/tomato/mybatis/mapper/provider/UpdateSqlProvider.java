package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;
import java.util.stream.Stream;

/**
 * 更新 provider
 *
 * @author lizhifu
 * @since 2023/5/18
 */
@Slf4j
public class UpdateSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param params  params
     * @param context context
     * @return  sql
     */
    public String sql(Map<String, Object> params, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            Object criteria = params.get("criteria");
            TableInfo table = tableInfo(context);
            SQL sql = new SQL()
                    .UPDATE(table.tableName)
                    .SET(Stream.of(table.fields)
                            .filter(field -> !table.primaryKeyColumn.equals(TableInfo.columnName(field)))
                            .map(TableInfo::assignParameter).toArray(String[]::new))
                    .WHERE(table.getPrimaryKeyEntityWhere());
            log.info("update sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}
