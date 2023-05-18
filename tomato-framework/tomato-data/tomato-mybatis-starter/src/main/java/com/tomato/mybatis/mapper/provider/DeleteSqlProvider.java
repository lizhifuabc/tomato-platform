package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

/**
 * 删除provider
 * @author lizhifu
 */
@Slf4j
public class DeleteSqlProvider extends BaseSqlProviderSupport {
    public String sql(ProviderContext context) {
        TableInfo table = tableInfo(context);
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            SQL sql = new SQL()
                    .DELETE_FROM(table.tableName)
                    .WHERE(table.primaryKeyColumn + " = #{id}");
            log.info("delete sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}
