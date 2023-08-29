package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;

/**
 * 根据条件删除
 *
 * @author lizhifu
 */
@Slf4j
public class DeleteByCriteriaSqlProvider extends AbstractSqlProviderSupport {

	/**
	 * sql
	 * @param params params 条件
	 * @param context context
	 * @return sql
	 */
	public String sql(Map<String, Object> params, ProviderContext context) {
		return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
			TableInfo table = tableInfo(context);
			StringBuilder builder = new StringBuilder("<script>\n");
			builder.append(String.format("delete from \n%s", table.tableName));
			builder.append(whereSql(table));
			builder.append("\n</script>");
			log.info("delete criteria sql:\n{}", builder);
			return builder.toString();
		});
	}

}