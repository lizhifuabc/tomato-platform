package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;
import java.util.stream.Stream;

/**
 * 只能新非空字段 provider TODO version
 *
 * @author lizhifu
 */
@Slf4j
public class UpdateSelectiveSqlProvider extends AbstractSqlProviderSupport {

	/**
	 * sql
	 * @param params params
	 * @param context context
	 * @return sql
	 */
	public String sql(Map<String, Object> params, ProviderContext context) {
		String cacheKey = getCacheKey(context);
		return SQL_CACHE.computeIfAbsent(cacheKey, value -> {
			TableInfo table = tableInfo(context);
			StringBuilder builder = new StringBuilder("<script>\n");
			builder.append(String.format("UPDATE \n%s", table.tableName));
			builder.append(updateSql(table));
			builder.append("\nWHERE ").append(table.getPrimaryKeyEntityWhere());
			builder.append("\n</script>");
			log.info("Mybatis通用Mapper|cacheKey:{}|update selective sql:\n{}", cacheKey, builder);
			return builder.toString();
		});
	}

}