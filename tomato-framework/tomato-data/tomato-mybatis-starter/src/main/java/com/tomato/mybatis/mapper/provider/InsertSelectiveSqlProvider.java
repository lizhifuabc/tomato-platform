package com.tomato.mybatis.mapper.provider;

import com.tomato.mybatis.mapping.TableInfo;
import com.tomato.mybatis.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * 插入非空字段
 * @author lizhifu
 */
@Slf4j
public class InsertSelectiveSqlProvider extends BaseSqlProviderSupport {
    /**
     * sql
     * @param entity  entity
     * @param context context
     * @return  sql
     */
    public String sql(Object entity, ProviderContext context) {
        TableInfo table = tableInfo(context);

        Field[] notNullFields = Stream.of(table.fields)
                .filter(field -> ReflectionUtils.getFieldValue(field, entity) != null && !table.primaryKeyColumn.equals(TableInfo.columnName(field)))
                .toArray(Field[]::new);

        return SQL_CACHE.computeIfAbsent(getCacheKey(context), val -> {
            SQL sql = new SQL()
                    .INSERT_INTO(table.tableName)
                    .INTO_COLUMNS(TableInfo.columns(notNullFields))
                    .INTO_VALUES(Stream.of(notNullFields).map(TableInfo::bindParameter).toArray(String[]::new));
            log.info("insert selective sql:{}",sql.toString());
            return sql.toString();
        });
    }
}