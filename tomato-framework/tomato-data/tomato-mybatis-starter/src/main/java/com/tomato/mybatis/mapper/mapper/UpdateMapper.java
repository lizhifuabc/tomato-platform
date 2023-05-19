package com.tomato.mybatis.mapper.mapper;

import com.tomato.mybatis.mapper.Mapper;
import com.tomato.mybatis.mapper.provider.UpdateSelectiveSqlProvider;
import com.tomato.mybatis.mapper.provider.UpdateSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 更新 Mapper
 *
 * @author lizhifu
 * @since 2023/5/18
 */
public interface UpdateMapper<T, ID> extends Mapper<T, ID> {
    /**
     * 根据主键id更新实体，若实体field为null，则对应数据库的字段也更新为null
     *
     * @param entity  实体对象
     * @return         影响条数
     */
    @UpdateProvider(type = UpdateSqlProvider.class, method = "sql")
    int updateByPrimaryKey(@Param("criteria") T entity);

    /**
     * 根据主键id更新实体，若实体field为null，则对应数据库的字段不更新
     *
     * @param entity  实体对象
     * @return        影响条数
     */
    @UpdateProvider(type = UpdateSelectiveSqlProvider.class, method = "sql")
    int updateByPrimaryKeySelective(@Param("criteria")T entity);
}
