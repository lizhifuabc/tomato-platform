package com.tomato.gen.dao;

import com.tomato.gen.domain.resp.TableColumnResp;
import com.tomato.gen.domain.resp.TableResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表列信息
 *
 * @author lizhifu
 * @since 2023/3/29
 */
@Mapper
public interface GenTableDao {

	/**
	 * 查询表是否存在
	 * @param tableName 表名
	 * @return 表数量
	 */
	long countByTableName(@Param("tableName") String tableName);

	/**
	 * 查询表列信息
	 * @param tableName 表名
	 * @return 列信息
	 */
	List<TableColumnResp> selectTableColumnByTableName(@Param("tableName") String tableName);

	/**
	 * 查询表信息
	 * @param tableName 表名
	 * @return 表信息
	 */
	TableResp selectTableByTableName(@Param("tableName") String tableName);

}
