package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.domain.Sort;
import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * 根据条件查询
 *
 * @author lizhifu
 */
@Slf4j
public class SelectByCriteriaSqlProvider extends AbstractSqlProviderSupport {

	/**
	 * sql
	 * @param criteria entity 条件
	 * @param context context
	 * @return sql
	 */
	public String sql(@Param("sort") Sort sort, @Param("criteria") Object criteria, ProviderContext context) {
		return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
			TableInfo table = tableInfo(context);
			StringBuilder builder = new StringBuilder("<script>\n");
			builder.append(
					String.format("select \n%s \n from \n%s", String.join(",", table.selectColumns), table.tableName));
			builder.append(whereSql(table));
			builder.append(orderBySql(sort));
			builder.append("\n</script>");
			log.info("selectByCriteria sql:\n{}", builder);
			return builder.toString();
		});
	}

}