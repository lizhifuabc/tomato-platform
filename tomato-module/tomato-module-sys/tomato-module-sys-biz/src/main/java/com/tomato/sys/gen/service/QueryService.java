package com.tomato.sys.gen.service;

import com.tomato.sys.constant.DbTypeEnum;

/**
 * 查询
 *
 * @author lizhifu
 * @since 2023/1/17
 */
public interface QueryService {
    /**
     * 数据库类型
     */
    DbTypeEnum dbType();

    /**
     * 表信息查询 SQL
     */
    String tableSql(String tableName);

    /**
     * 表名称
     */
    String tableName();

    /**
     * 表注释
     */
    String tableComment();

    /**
     * 表字段信息查询 SQL
     */
    String tableFieldsSql();

    /**
     * 字段名称
     */
    String fieldName();

    /**
     * 字段类型
     */
    String fieldType();

    /**
     * 字段注释
     */
    String fieldComment();

    /**
     * 主键字段
     */
    String fieldKey();
}
