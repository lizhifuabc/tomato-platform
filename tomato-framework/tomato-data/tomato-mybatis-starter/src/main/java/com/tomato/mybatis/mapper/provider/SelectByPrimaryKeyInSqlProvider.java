package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.domain.Sort;
import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;

/**
 * 根据id列表查询
 * @author lizhifu
 */
@Slf4j
public class SelectByPrimaryKeyInSqlProvider extends AbstractSqlProviderSupport {
    public String sql(Map<String, Object> params, ProviderContext context) {
        return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
            TableInfo table = tableInfo(context);
            Sort sort = Sort.by(table.primaryKeyColumn);
            StringBuilder builder = new StringBuilder("<script>\n");
            builder.append(tableSql(table));
            builder.append("\nWHERE ").append(table.primaryKeyColumn).append(" IN ").append(inSql());
            builder.append(orderBySql(sort));
            builder.append("\n</script>");
            log.info("select primary sql:\n{}",builder);
            return builder.toString();
        });
    }
}