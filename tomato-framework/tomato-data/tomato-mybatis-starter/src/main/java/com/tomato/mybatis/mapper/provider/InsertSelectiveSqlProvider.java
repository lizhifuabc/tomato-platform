package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 插入非空字段
 * @author lizhifu
 */
@Slf4j
public class InsertSelectiveSqlProvider extends AbstractSqlProviderSupport {
    /**
     * sql
     * @param params  params 条件
     * @param context context
     * @return  sql
     */
    public String sql(Map<String, Object> params, ProviderContext context) {
        // TODO 缓存需要添加size,此时存在问题，如果第一次插入的是2条数据，第二次插入的是3条数据，那么第二次插入的sql会被缓存
        // TODO 如果数据比较多，那么缓存的sql会很多，需要考虑缓存的问题
        Object criteria = params.get("criteria");
        TableInfo table = tableInfo(context);
        Field[] notNullFields = Stream.of(table.fields)
                .filter(field -> ReflectionUtils.getFieldValue(field, criteria) != null && !table.primaryKeyColumn.equals(TableInfo.columnName(field)))
                .toArray(Field[]::new);
        SQL sql = new SQL()
                .INSERT_INTO(table.tableName)
                .INTO_COLUMNS(TableInfo.columns(notNullFields))
                .INTO_VALUES(Stream.of(notNullFields).map(TableInfo::bindParameter).toArray(String[]::new));
        log.info("insert selective sql:\n{}",sql.toString());
        return sql.toString();
    }
}