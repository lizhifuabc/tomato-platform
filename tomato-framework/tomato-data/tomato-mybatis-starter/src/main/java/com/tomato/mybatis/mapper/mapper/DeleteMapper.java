package com.tomato.mybatis.mapper.mapper;

import com.tomato.mybatis.mapper.Mapper;
import com.tomato.mybatis.mapper.provider.DeleteByCriteriaSqlProvider;
import com.tomato.mybatis.mapper.provider.DeleteSqlProvider;
import com.tomato.mybatis.mapper.provider.LogicDeleteSqlProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 删除Mapper
 *
 * @author lizhifu
 * @since 2023/5/18
 */
public interface DeleteMapper<T, ID> extends Mapper<T, ID> {
    /**
     * 根据主键删除
     *
     * @param id  id
     * @return  影响条数
     */
    @DeleteProvider(type = DeleteSqlProvider.class, method = "sql")
    int deleteByPrimaryKey(ID id);

    /**
     * 逻辑删除，将is_deleted字段更新为1
     *
     * @param id id
     * @return  影响条数
     */
    @UpdateProvider(type = LogicDeleteSqlProvider.class, method = "sql")
    int logicDeleteByPrimaryKey(ID id);

    /**
     * 根据实体条件删除
     *
     * @param criteria  实体
     * @return  影响条数
     */
    @DeleteProvider(type = DeleteByCriteriaSqlProvider.class, method = "sql")
    int deleteByCriteria(T criteria);
}
