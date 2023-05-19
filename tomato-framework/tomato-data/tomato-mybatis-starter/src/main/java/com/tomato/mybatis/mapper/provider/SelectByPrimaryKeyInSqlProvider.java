package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

/**
 * 根据id列表查询
 * @author lizhifu
 */
@Slf4j
public class SelectByPrimaryKeyInSqlProvider extends BaseSqlProviderSupport {
    public String sql(Map<String, Object> params, ProviderContext context) {
        TableInfo table = tableInfo(context);
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            @SuppressWarnings("unchecked")
            List<Object> ids = (List<Object>)params.get("ids");
            SQL sql = new SQL()
                    .SELECT(table.selectColumns)
                    .FROM(table.tableName)
                    .WHERE(table.primaryKeyColumn
                            + " IN (" + String.join(",", ids.stream().map(String::valueOf).toArray(String[]::new)) +")");
            log.info("select primary sql:\n{}",sql.toString());
            return sql.toString();
        });
    }
}