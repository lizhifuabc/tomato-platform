package com.tomato.mybatis.mapper.mapper;

import com.tomato.mybatis.domain.Sort;
import com.tomato.mybatis.mapper.Mapper;
import com.tomato.mybatis.mapper.provider.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Optional;

/**
 * 查询 Mapper
 *
 * @author lizhifu
 * @since 2023/5/18
 */
public interface SelectMapper<T, ID> extends Mapper<T, ID> {
    /**
     * 根据id查询实体
     *
     * @param id  id
     * @return    实体
     */
    @SelectProvider(type = SelectOneSqlProvider.class, method = "sql")
    Optional<T> selectByPrimaryKey(ID id);

    /**
     * 查询所有实体
     *
     * @param sort  排序
     * @return   实体list
     */
    @SelectProvider(type = SelectAllSqlProvider.class, method = "sql")
    List<T> selectAll(Sort sort);

    /**
     * 根据id列表查询实体列表
     * @param ids  id列表
     * @return  list
     */
    @SelectProvider(type = SelectByPrimaryKeyInSqlProvider.class, method = "sql")
    List<T> selectByPrimaryKeyIn(@Param("ids") List<ID> ids);

    /**
     * 根据实体条件查询符合条件的实体list
     * @param criteria  条件实体
     * @param orderBy   排序 如：id desc
     * @return          list
     */
    @SelectProvider(type = SelectByCriteriaSqlProvider.class, method = "sql")
    List<T> selectByCriteria(@Param("orderBy") String orderBy,@Param("criteria") T criteria);

    /**
     * 根据条件查询单个数据
     *
     * @param criteria  实体条件
     * @return          实体对象
     */
    @SelectProvider(type = SelectOneByCriteriaSqlProvider.class, method = "sql")
    Optional<T> selectOneByCriteria(@Param("criteria") T criteria);
}
