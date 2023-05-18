package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.stream.Stream;

/**
 * 更新 provider
 *
 * @author lizhifu
 * @since 2023/5/18
 */
@Slf4j
public class UpdateSqlProvider extends BaseSqlProviderSupport {
    /**
     * sql
     * @param context context
     * @return  sql
     */
    public String sql(ProviderContext context) {
        TableInfo table = tableInfo(context);
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            SQL sql = new SQL()
                    .UPDATE(table.tableName)
                    .SET(Stream.of(table.fields)
                            .filter(field -> !table.primaryKeyColumn.equals(TableInfo.columnName(field)))
                            .map(TableInfo::assignParameter).toArray(String[]::new))
                    .WHERE(table.getPrimaryKeyWhere());
            log.info("update sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}
