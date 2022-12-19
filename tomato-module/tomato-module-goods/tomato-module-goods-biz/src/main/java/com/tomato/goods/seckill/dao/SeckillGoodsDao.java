package com.tomato.goods.seckill.dao;

import com.tomato.goods.domain.entity.SeckillGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 秒杀活动商品{@link SeckillGoodsEntity}
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Mapper
public interface SeckillGoodsDao {
    /**
     * 插入
     * @param seckillGoods 秒杀活动商品
     */
    public void insert(@Param("seckillGoods") SeckillGoodsEntity seckillGoods);
    /**
     * 批量插入
     * @param goodsList 秒杀活动商品
     */
    public void insertList(@Param("goodsList") List<SeckillGoodsEntity> goodsList);
    /**
     * 更新秒杀剩余量
     * @return 更新数量
     */
    public int updateSkillRemaining(@Param("id") Long id,@Param("version") Integer version);
}
