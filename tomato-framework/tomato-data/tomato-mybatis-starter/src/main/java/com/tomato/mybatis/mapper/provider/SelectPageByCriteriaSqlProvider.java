package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.domain.Sort;
import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.util.Map;

/**
 * 分页查询
 *
 * @author lizhifu
 */
@Slf4j
public class SelectPageByCriteriaSqlProvider extends AbstractSqlProviderSupport {

	/**
	 * sql
	 * @param params param 条件
	 * @param context context
	 * @return sql
	 */
	public String sql(Map<String, Object> params, ProviderContext context) {
		return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
			Object criteria = params.get("criteria");
			Page page = (Page) params.get("page");
			TableInfo table = tableInfo(context);
			Sort sort = page.getSort();
			StringBuilder builder = new StringBuilder("<script>\n");
			builder.append(
					String.format("select \n%s \n from \n%s", String.join(",", table.selectColumns), table.tableName));
			builder.append(whereSql(table));
			builder.append(orderBySql(sort));
			builder.append(limitSql());
			builder.append("\n</script>");
			log.info("select page criteria sql:\n{}", builder);
			return builder.toString();
		});
	}

}