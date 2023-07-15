package com.tomato.seckill.goods.application.cache;

/**
 * 缓存接口
 *
 * @author lizhifu
 * @since 2023/7/14
 */
public interface SeckillCacheService {
    /**
     * 构建缓存的key
     */
    String buildCacheKey(Object key);

    /**
     * 构建缓存分布式锁的key
     */
    default String buildCacheLockKey(Object key){
        return buildCacheKey(key) + ":lock";
    }
}
