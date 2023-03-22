package com.tomato.seckill.service.cache;

import com.tomato.domain.resp.MultiResp;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.goods.feign.RemoteGoodsService;
import com.tomato.lock.core.exe.DistributedLockExe;
import com.tomato.seckill.constant.RedisConstant;
import com.tomato.seckill.dao.SeckillGoodsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 秒杀活动商品缓存
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@Service
@Slf4j
public class SeckillGoodsInfoCacheServiceB {
    private final RemoteGoodsService remoteGoodsService;
    private final SeckillGoodsDao seckillGoodsDao;
    private final DistributedLockExe distributedLockExe;
    public SeckillGoodsInfoCacheServiceB(RemoteGoodsService remoteGoodsService, SeckillGoodsDao seckillGoodsDao, DistributedLockExe distributedLockExe) {
        this.remoteGoodsService = remoteGoodsService;
        this.seckillGoodsDao = seckillGoodsDao;
        this.distributedLockExe = distributedLockExe;
    }

    /**
     * 缓存
     * 秒杀的数据量不大，可以直接缓存到redis中
     * TODO 优化：秒杀的数据量大的时候，mongoDB,es 等等
     * @param seckillActivityId 秒杀活动id
     */
    public void cache(Long seckillActivityId){
        // 1. redis 分布式锁
        String key = RedisConstant.SECKILL_GOODS_INFO + seckillActivityId;
        try {
            boolean resLock = (boolean) distributedLockExe.lock(key, 10,10);
            log.info("redis 分布式锁,key:{},resLock:{}",key,resLock);
            if (resLock){
                List<Long> idList = seckillGoodsDao.selectIdBySeckillActivityId(seckillActivityId);
            }
        }catch (Exception e) {
            log.error("redis 分布式锁,key:{},e:{}", key, e);
        }finally {
            // 删除 redis 分布式锁
            boolean resUnlock = distributedLockExe.unLock(key,false);
            log.info("redis 分布式锁,key:{},resUnlock:{}",key,resUnlock);
        }
    }
}
