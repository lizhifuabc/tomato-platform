package com.tomato.seckill.goods.application.service.impl;

import com.tomato.seckill.goods.application.builder.SeckillGoodsBuilder;
import com.tomato.seckill.goods.application.req.SeckillGoodsCreateReq;
import com.tomato.seckill.goods.application.service.SeckillGoodsService;
import com.tomato.seckill.goods.domain.entity.SeckillGoods;
import com.tomato.seckill.goods.domain.service.SeckillGoodsDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品服务
 *
 * @author lizhifu
 * @since 2023/7/14
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    private SeckillGoodsDomainService seckillGoodsDomainService;

    @Override
    public void saveSeckillGoods(SeckillGoodsCreateReq seckillGoodsCreateReq) {
        // 秒杀活动是否存在校验
        // 将商品的库存同步到Redis
        // 商品限购同步到Redis
        // 保存商品到数据库
        SeckillGoods seckillGoods = SeckillGoodsBuilder.toSeckillGoods(seckillGoodsCreateReq);
        seckillGoodsDomainService.saveSeckillGoods(seckillGoods);
    }
}
