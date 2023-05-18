package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

/**
 * 逻辑删除
 * @author lizhifu
 */
@Slf4j
public class LogicDeleteSqlProvider extends BaseSqlProviderSupport {
    public String sql(ProviderContext context) {
        TableInfo table = tableInfo(context);
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            SQL sql = new SQL()
                    .UPDATE(table.tableName)
                    .SET("is_deleted = 1")
                    .WHERE(table.primaryKeyColumn + " = #{id}");
            log.info("logic delete sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}