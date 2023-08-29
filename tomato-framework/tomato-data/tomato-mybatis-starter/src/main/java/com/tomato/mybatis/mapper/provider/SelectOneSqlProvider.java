package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

/**
 * 单条数据查询
 *
 * @author lizhifu
 */
@Slf4j
public class SelectOneSqlProvider extends AbstractSqlProviderSupport {

	/**
	 * sql
	 * @param context context
	 * @return sql
	 */
	public String sql(ProviderContext context) {
		return SQL_CACHE.computeIfAbsent(getCacheKey(context), value -> {
			TableInfo table = tableInfo(context);
			SQL sql = new SQL().SELECT(table.selectColumns).FROM(table.tableName).WHERE(table.getPrimaryKeyWhere());
			log.info("selectOne sql:\n{}", sql.toString());
			return sql.toString();
		});
	}

}