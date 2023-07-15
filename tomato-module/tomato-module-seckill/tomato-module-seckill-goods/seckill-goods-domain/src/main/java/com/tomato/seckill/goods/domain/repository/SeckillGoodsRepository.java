package com.tomato.seckill.goods.domain.repository;

import com.tomato.seckill.goods.domain.entity.SeckillGoods;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2023/7/14
 */
public interface SeckillGoodsRepository {
    /**
     * 保存秒杀商品
     * @param seckillGoods 秒杀商品
     */
    int saveSeckillGoods(SeckillGoods seckillGoods);
}
