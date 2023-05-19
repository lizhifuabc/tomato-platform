package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;
import java.util.stream.Stream;

/**
 * 根据条件删除
 * @author lizhifu
 */
@Slf4j
public class DeleteByCriteriaSqlProvider extends BaseSqlProviderSupport {
    /**
     * sql
     * @param params  params 条件
     * @param context context
     * @return  sql
     */
    public String sql(Map<String, Object> params, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            Object criteria = params.get("criteria");
            SQL sql = new SQL()
                    .DELETE_FROM(table.tableName)
                    .WHERE(Stream.of(table.fields)
                            .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null)
                            .map(TableInfo::assignParameter)
                            .toArray(String[]::new));
            log.info("delete criteria sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}