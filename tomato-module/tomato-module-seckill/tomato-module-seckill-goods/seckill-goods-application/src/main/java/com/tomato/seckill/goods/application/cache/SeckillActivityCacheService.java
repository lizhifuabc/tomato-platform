package com.tomato.seckill.goods.application.cache;

import com.tomato.seckill.domain.CommonCache;
import com.tomato.seckill.goods.domain.entity.SeckillActivity;

/**
 * 秒杀活动缓存接口
 *
 * @author lizhifu
 * @since 2023/7/15
 */
public interface SeckillActivityCacheService extends SeckillCacheService {
    /**
     * 根据id获取活动信息
     * @param activityId 活动id
     * @param version 版本号,缓存时间
     * @return SeckillActivity 秒杀活动
     */
    CommonCache<SeckillActivity> getLocalOrDistributedCache(Long activityId, Long version);

    /**
     * 更新缓存数据
     * @param activityId 活动id
     * @param doubleCheck 是否需要二次校验
     * @return SeckillActivity 秒杀活动
     */
    CommonCache<SeckillActivity> tryUpdate(Long activityId, boolean doubleCheck);

    /**
     * 获取分布式缓存
     * @param activityId 活动id
     * @return SeckillActivity 秒杀活动
     */
    CommonCache<SeckillActivity> getDistributedCache(Long activityId);
}
