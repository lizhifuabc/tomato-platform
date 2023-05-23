package com.tomato.mybatis.mapper.mapper;

import com.tomato.mybatis.mapper.Mapper;
import com.tomato.mybatis.mapper.provider.SelectPageByCriteriaSqlProvider;
import com.tomato.mybatis.paginate.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 分页 Mapper
 *
 * @author lizhifu
 * @since 2023/5/19
 */
public interface PageMapper<T, ID> extends Mapper<T, ID> {
    /**
     * 根据实体条件查询符合条件的实体list
     * @param criteria  条件实体
     * @param page      分页对象
     * @return          list
     */
    @SelectProvider(type = SelectPageByCriteriaSqlProvider.class, method = "sql")
    List<T> selectPageByCriteria(@Param("page") Page page, @Param("criteria") T criteria);
}
