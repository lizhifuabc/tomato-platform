package com.tomato.goods.seckill.service;

import com.tomato.domain.exception.BusinessException;
import com.tomato.goods.domain.entity.SeckillGoodsEntity;
import com.tomato.goods.seckill.manager.SeckillGoodsRedisManager;
import org.springframework.stereotype.Service;

/**
 * 抢购 redis 校验 TODO
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Service
public class SeckillRedisCheckService {
    private final SeckillGoodsRedisManager seckillGoodsRedisManager;

    public SeckillRedisCheckService(SeckillGoodsRedisManager seckillGoodsRedisManager) {
        this.seckillGoodsRedisManager = seckillGoodsRedisManager;
    }
    /**
     * Redis 库存校验
     * @param seckillGoodsId  秒杀活动商品ID {@link SeckillGoodsEntity }
     * @param seckillActivityId 活动ID
     */
    public void checkSeckillGoods(Long seckillGoodsId,Long seckillActivityId){
        Long remaining = seckillGoodsRedisManager.seckillRemaining(seckillGoodsId, seckillActivityId);
        if (remaining <= 0){
            throw new BusinessException("商品库存不足");
        }
    }
}
