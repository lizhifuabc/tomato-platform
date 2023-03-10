package com.tomato.sys.gen.service.impl;

import com.tomato.sys.constant.DbTypeEnum;
import com.tomato.sys.gen.service.QueryService;

/**
 * MySQL 查询
 *
 * @author lizhifu
 * @since 2023/1/17
 */
public class MySqlQueryImpl implements QueryService {
    @Override
    public DbTypeEnum dbType() {
        return DbTypeEnum.MySQL;
    }

    @Override
    public String tableSql(String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select table_name, table_comment from information_schema.tables ");
        sql.append("where table_schema = (select database()) ");
        sql.append("and table_name = '").append(tableName).append("' ");
        sql.append("order by table_name asc");
        return sql.toString();
    }

    @Override
    public String tableName() {
        return "table_name";
    }

    @Override
    public String tableComment() {
        return "table_comment";
    }

    @Override
    public String tableFieldsSql() {
        return "select column_name, data_type, column_comment, column_key from information_schema.columns "
                + "where table_name = '%s' and table_schema = (select database()) order by ordinal_position";
    }

    @Override
    public String fieldName() {
        return "column_name";
    }

    @Override
    public String fieldType() {
        return "data_type";
    }

    @Override
    public String fieldComment() {
        return "column_comment";
    }

    @Override
    public String fieldKey() {
        return "column_key";
    }
}
