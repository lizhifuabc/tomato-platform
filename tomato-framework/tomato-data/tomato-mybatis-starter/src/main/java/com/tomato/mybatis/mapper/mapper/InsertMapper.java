package com.tomato.mybatis.mapper.mapper;

import com.tomato.mybatis.mapper.Mapper;
import com.tomato.mybatis.mapper.provider.*;
import com.tomato.mybatis.mapping.TableInfo;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 插入 Mapper
 *
 * @author lizhifu
 * @since 2023/5/18
 */
public interface InsertMapper<T, ID> extends Mapper<T, ID> {
    /**
     * 插入新对象,并返回主键id值(id通过实体获取)
     *
     * @param entity 实体对象
     * @return  影响条数
     */
    @InsertProvider(type = InsertSqlProvider.class, method = "sql")
    @Options(useGeneratedKeys = true, keyColumn = TableInfo.DEFAULT_PRIMARY_KEY, keyProperty = TableInfo.DEFAULT_PRIMARY_KEY)
    int insert(@Param("criteria") T entity);

    /**
     * 插入新对象（只设置非空字段）,并返回主键id值(id通过实体获取)
     *
     * @param criteria 实体对象
     * @return  影响条数
     */
    @InsertProvider(type = InsertSelectiveSqlProvider.class, method = "sql")
    @Options(useGeneratedKeys = true, keyColumn = TableInfo.DEFAULT_PRIMARY_KEY, keyProperty = TableInfo.DEFAULT_PRIMARY_KEY)
    int insertSelective(@Param("criteria") T criteria);

    /**
     * 批量插入实体
     *
     * @param entities  实体列表
     * @return          影响条数
     */
    @InsertProvider(type = BatchInsertSqlProvider.class, method = "sql")
    int batchInsert(@Param("entities") List<T> entities);
}
