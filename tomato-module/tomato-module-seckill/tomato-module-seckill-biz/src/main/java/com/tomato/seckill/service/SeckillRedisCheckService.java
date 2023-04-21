package com.tomato.seckill.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.common.resp.Resp;
import com.tomato.redis.domain.req.RedisConcurrentRequestCountLimiterReq;
import com.tomato.redis.domain.resp.RedisConcurrentRequestCountLimiterResp;
import com.tomato.redis.ratelimit.RedisConcurrentRequestCountLimiter;
import com.tomato.seckill.domain.entity.SeckillGoodsEntity;
import com.tomato.seckill.manager.SeckillGoodsCacheManager;
import org.springframework.stereotype.Service;

/**
 * 抢购 redis 校验 TODO
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Service
public class SeckillRedisCheckService {
    private final SeckillGoodsCacheManager seckillGoodsCacheManager;
    private final RedisConcurrentRequestCountLimiter redisConcurrentRequestCountLimiter;

    public SeckillRedisCheckService(SeckillGoodsCacheManager seckillGoodsCacheManager, RedisConcurrentRequestCountLimiter redisConcurrentRequestCountLimiter) {
        this.seckillGoodsCacheManager = seckillGoodsCacheManager;
        this.redisConcurrentRequestCountLimiter = redisConcurrentRequestCountLimiter;
    }
    /**
     * Redis 库存校验
     * @param seckillGoodsId  秒杀活动商品ID {@link SeckillGoodsEntity }
     * @param seckillActivityId 活动ID
     */
    public void checkSeckillGoods(Long seckillGoodsId,Long seckillActivityId){
        Long remaining = seckillGoodsCacheManager.seckillRemaining(seckillGoodsId, seckillActivityId);
        if (remaining <= 0){
            throw new BusinessException("商品库存不足");
        }
    }

    /**
     * 同一ip限流 & 同一用户限流
     * @param ip ip
     * @param userId userId
     */
    public void checkUserIp(String ip, Long userId) {
        RedisConcurrentRequestCountLimiterReq userIp = RedisConcurrentRequestCountLimiterReq.builder()
                .id("SECKILL:" + userId.toString()+":"+ip)
                .count(1)
                .interval(5)
                .build();
        Resp<RedisConcurrentRequestCountLimiterResp> allowed = redisConcurrentRequestCountLimiter.isAllowed(userIp);
        if(allowed.isSuccess() && allowed.getData().isAllowed()){
            return;
        }
        throw new BusinessException("请稍后重试");
    }
}
