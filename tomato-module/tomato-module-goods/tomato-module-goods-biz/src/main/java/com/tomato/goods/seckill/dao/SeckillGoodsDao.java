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
    void insert(@Param("seckillGoods") SeckillGoodsEntity seckillGoods);
    /**
     * 批量插入
     * @param goodsList 秒杀活动商品
     */
    void insertList(@Param("goodsList") List<SeckillGoodsEntity> goodsList);
    /**
     * 更新秒杀剩余量
     * @return 更新数量
     */
    int updateSkillRemaining(@Param("id") Long id,@Param("version") Integer version);

    /**
     * 根据活动id查询
     * @param seckillActivityId 活动id
     * @return 商品列表
     */
    List<SeckillGoodsEntity> selectBySeckillActivityId(@Param("seckillActivityId") Long seckillActivityId);

    /**
     * 根据活动ID,商品ID查询
     * @param goodsId 商品ID
     * @param seckillActivityId 活动ID
     * @return 商品
     */
    SeckillGoodsEntity selectBySeckillActivityIdGoodsId(@Param("goodsId") Long goodsId,@Param("seckillActivityId") Long seckillActivityId);
    /**
     * 根据ID查询
     * @param id ID
     * @return 商品
     */
    SeckillGoodsEntity selectById(@Param("id") Long id);
}
