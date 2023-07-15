package com.tomato.seckill.goods.application.cache.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tomato.jackson.utils.JacksonUtils;
import com.tomato.redis.redisson.lock.DistributedLock;
import com.tomato.redis.redisson.lock.DistributedLockFactory;
import com.tomato.seckill.domain.CommonCache;
import com.tomato.seckill.goods.application.cache.SeckillActivityCacheService;
import com.tomato.seckill.goods.domain.entity.SeckillActivity;
import com.tomato.seckill.goods.domain.service.SeckillActivityDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.tomato.seckill.constant.CacheConstant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 秒杀活动缓存接口实现
 *
 * @author lizhifu
 * @since 2023/7/15
 */
@Service
@Slf4j
public class SeckillActivityCacheServiceImpl implements SeckillActivityCacheService {
    @Autowired
    private SeckillActivityDomainService seckillActivityDomainService;
    @Autowired
    private DistributedLockFactory distributedLockFactory;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 本地可重入锁
     */
    private final Lock localCacheUpdatelock = new ReentrantLock();
    /**
     * 本地缓存
     * initialCapacity 初始化容量,初始化容量是指缓存中能够存储的元素数量,实际数量会根据缓存中元素的数量动态调整
     * concurrencyLevel 并发级别,并发级别是指可以同时写缓存的线程数,超过这个并发数的线程会在缓存写入操作时被阻塞,并且写缓存操作会被串行化
     * expireAfterWrite 写入后过期时间,写入后多久过期,单位是秒
     */
    private static final Cache<String, CommonCache<SeckillActivity>> CACHE = CacheBuilder.newBuilder()
            .initialCapacity(15)
            .concurrencyLevel(5)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();

    @Override
    public CommonCache<SeckillActivity> getLocalOrDistributedCache(Long activityId, Long version) {
        // 本地缓存中获取数据
        CommonCache<SeckillActivity> seckillActivityCache = CACHE.getIfPresent(buildCacheKey(activityId));
        // 版本号为空，则直接返回本地缓存中的数据
        if (version == null && seckillActivityCache != null) {
            log.info("秒杀活动|本地缓存|{}", activityId);
            return seckillActivityCache;
        }
        // 传递的版本号小于等于缓存中的版本号，则说明缓存中的数据比客户端的数据新，直接返回本地缓存中的数据
        if (seckillActivityCache != null && seckillActivityCache.getVersion() >= version) {
            log.info("秒杀活动|本地缓存数据已经更新，返回新的本地缓存数据|{}", activityId);
            return seckillActivityCache;
        }
        // 传递的版本号大于缓存中的版本号，说明缓存中的数据比较落后，从分布式缓存获取数据并更新到本地缓存
        // 从分布式缓存中获取数据，并设置到本地缓存中
        return getDistributedCache(activityId);
    }

    @Override
    public CommonCache<SeckillActivity> tryUpdate(Long activityId, boolean doubleCheck) {
        log.info("秒杀活动|更新分布式缓存|{}", activityId);
        // 获取分布式锁
        DistributedLock lock = distributedLockFactory.getLock(buildCacheLockKey(activityId));
        try {
            // 尝试获取锁，最多等待1秒，上锁以后5秒自动解锁
            boolean isLockSuccess = lock.tryLock(1, 5, TimeUnit.SECONDS);
            //未获取到分布式锁的线程快速返回，不占用系统资源
            if (!isLockSuccess){
                log.info("秒杀活动|获取分布式锁失败|{}", activityId);
                return new CommonCache<SeckillActivity>().retryLater();
            }
            CommonCache<SeckillActivity> seckillActivityCache;
            if (doubleCheck){
                // 获取锁成功后，再次从缓存中获取数据，防止高并发下多个线程争抢锁的过程中，后续的线程再等待1秒的过程中，前面的线程释放了锁，
                // 后续的线程获取锁成功后再次更新分布式缓存数据
                seckillActivityCache = JacksonUtils.toObject((String) redisTemplate.opsForValue().get(buildCacheKey(activityId)), new TypeReference<CommonCache<SeckillActivity>>() {});
                if (seckillActivityCache != null){
                    return seckillActivityCache;
                }
            }
            SeckillActivity seckillActivity = seckillActivityDomainService.getSeckillActivityById(activityId);
            if (seckillActivity == null){
                seckillActivityCache = new CommonCache<SeckillActivity>().notExist();
            }else{
                seckillActivityCache = new CommonCache<SeckillActivity>().with(seckillActivity).withVersion(System.currentTimeMillis());
            }
            // 将数据保存到分布式缓存
            redisTemplate.opsForValue().set(buildCacheKey(activityId), JacksonUtils.toJson(seckillActivityCache), CacheConstant.SECKILL_ACTIVITY_CACHE_KEY_TIME_OUT, TimeUnit.SECONDS);
            log.info("秒杀活动|分布式缓存已经更新|{}", activityId);
            return seckillActivityCache;
            //将数据放入分布式缓存
        } catch (InterruptedException e) {
            log.error("秒杀活动|更新分布式缓存失败|{}", activityId);
            return new CommonCache<SeckillActivity>().retryLater();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public CommonCache<SeckillActivity> getDistributedCache(Long activityId) {
        log.info("秒杀活动|从分布式缓存中获取数据|{}", activityId);
        // 从redis中获取数据
        CommonCache<SeckillActivity> seckillActivityCache = JacksonUtils.toObject((String) redisTemplate.opsForValue().get(buildCacheKey(activityId)), new TypeReference<CommonCache<SeckillActivity>>() {});
        // 分布式缓存中没有数据,尝试更新分布式缓存中的数据
        if(seckillActivityCache == null){
            seckillActivityCache = tryUpdate(activityId,true);
        }
        // 获取的数据不为空，并且不需要重试
        if(seckillActivityCache != null && !seckillActivityCache.isRetryLater()){
            // 设置到本地缓存中
            if (localCacheUpdatelock.tryLock()){
                try {
                    CACHE.put(buildCacheKey(activityId), seckillActivityCache);
                }finally {
                    localCacheUpdatelock.unlock();
                }
            }
        }
        return seckillActivityCache;
    }

    @Override
    public String buildCacheKey(Object key) {
        return CacheConstant.SECKILL_ACTIVITY_CACHE_KEY + "_" + key;
    }
}
