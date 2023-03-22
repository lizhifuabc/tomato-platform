package com.tomato.goods.dao;

import com.tomato.goods.domain.entity.GoodsInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品信息dao
 *
 * @author lizhifu
 * @since 2023/3/21
 */
@Mapper
public interface GoodsInfoDao {
    /**
     * 根据id查询
     * @param id 商品id
     * @return 商品信息
     */
    GoodsInfoEntity selectById(Long id);
    /**
     * 批量查询
     * @param ids 商品id
     * @return 商品信息
     */
    List<GoodsInfoEntity> selectBatchByIds(List<Long> ids);
}
