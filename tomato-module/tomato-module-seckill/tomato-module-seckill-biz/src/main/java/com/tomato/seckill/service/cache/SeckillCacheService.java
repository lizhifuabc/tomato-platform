package com.tomato.seckill.service.cache;

import com.tomato.domain.resp.SingleResp;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.goods.feign.RemoteGoodsService;
import com.tomato.lock.core.exe.DistributedLockExe;
import com.tomato.seckill.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 秒杀活动缓存
 *
 * @author lizhifu
 * @since 2023/3/17
 */
@Service
@Slf4j
public class SeckillCacheService {
    private final DistributedLockExe distributedLockExe;

    private final RemoteGoodsService remoteGoodsService;

    public SeckillCacheService(DistributedLockExe distributedLockExe, RemoteGoodsService remoteGoodsService) {
        this.distributedLockExe = distributedLockExe;
        this.remoteGoodsService = remoteGoodsService;
    }

    /**
     * 缓存预热
     * @param seckillActivityId 秒杀活动id
     */
    public void cacheWarmUp(Long seckillActivityId){
        // 1. redis 分布式锁
        String key = RedisConstant.SECKILL_GOODS_INFO + seckillActivityId;
        boolean resLock = (boolean) distributedLockExe.lock(key, 10,10);
        log.info("redis 分布式锁,key:{},resLock:{}",key,resLock);

        // 删除 redis 分布式锁
        boolean resUnlock = distributedLockExe.unLock(key,false);
        log.info("redis 分布式锁,key:{},resUnlock:{}",key,resUnlock);
        // 2. 缓存秒杀活动信息

        SingleResp<GoodsInfoResp> goodsInfoRespSingleResp = remoteGoodsService.queryGoodsInfo(1L);
        log.info("goodsInfoRespSingleResp:{}",goodsInfoRespSingleResp);
    }
}
