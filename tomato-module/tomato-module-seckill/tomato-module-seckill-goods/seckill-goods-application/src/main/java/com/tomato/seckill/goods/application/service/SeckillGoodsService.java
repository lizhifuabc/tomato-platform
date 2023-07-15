package com.tomato.seckill.goods.application.service;

import com.tomato.seckill.goods.application.req.SeckillGoodsCreateReq;

/**
 * 商品接口
 *
 * @author lizhifu
 * @since 2023/7/14
 */
public interface SeckillGoodsService {
    /**
     * 保存秒杀商品
     * @param seckillGoodsCreateReq 请求参数
     */
    void saveSeckillGoods(SeckillGoodsCreateReq seckillGoodsCreateReq);
}
