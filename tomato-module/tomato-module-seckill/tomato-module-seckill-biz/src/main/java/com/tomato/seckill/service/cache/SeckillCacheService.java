package com.tomato.seckill.service.cache;

import com.tomato.lock.core.exe.DistributedLockExe;
import com.tomato.seckill.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    public SeckillCacheService(DistributedLockExe distributedLockExe) {
        this.distributedLockExe = distributedLockExe;
    }

    // 缓存预热
    public void cacheWarmUp(Long seckillActivityId){
        // 1. redis 分布式锁
        distributedLockExe.lock(RedisConstant.SECKILL_GOODS_INFO + seckillActivityId, 10,10);
        // 2. 缓存秒杀活动信息
    }
}
