package com.tomato.mybatis.mapper.mapper;

import com.tomato.mybatis.mapper.Mapper;
import com.tomato.mybatis.mapper.provider.CountByCriteriaSqlProvider;
import com.tomato.mybatis.mapper.provider.CountSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * 统计 Mapper
 *
 * @author lizhifu
 * @since 2023/5/18
 */
public interface CountMapper<T, ID> extends Mapper<T, ID> {

    /**
     * 返回实体总数
     *
     * @return  总数
     */
    @SelectProvider(type = CountSqlProvider.class, method = "sql")
    long count();

    /**
     * 根据条件查询符合条件的实体总数
     *
     * @param criteria  实体条件
     * @return    数量
     */
    @SelectProvider(type = CountByCriteriaSqlProvider.class, method = "sql")
    long countByCriteria(@Param("criteria") T criteria);
}
