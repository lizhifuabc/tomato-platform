package com.tomato.seckill.strategy;

import com.tomato.seckill.domain.entity.SeckillGoodsEntity;

/**
 * 秒杀实现方式工厂
 *
 * @author lizhifu
 * @since 2023/3/17
 */
public interface SeckillStrategyFactory {
    /**
     * 库存校验
     * @param seckillGoodsId 秒杀活动商品ID {@link SeckillGoodsEntity }
     * @param seckillActivityId 秒杀活动ID {@link com.tomato.seckill.domain.entity.SeckillActivityEntity }
     */
    void checkSeckillGoods(Long seckillGoodsId,Long seckillActivityId);
    /**
     * 用户限购校验
     * @param userId 用户ID
     * @param seckillGoodsId 秒杀活动商品ID {@link SeckillGoodsEntity }
     */
    void checkUserLimit(Long userId,Long seckillGoodsId);

    /**
     * 扣减库存
     * @param seckillGoodsId 秒杀活动商品ID {@link SeckillGoodsEntity }
     * @param userId 用户ID
     */
    void deductSeckillGoods(Long seckillGoodsId,Long userId);
}
