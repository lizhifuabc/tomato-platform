package com.tomato.seckill.goods.domain.service;

import com.tomato.seckill.goods.domain.entity.SeckillActivity;

/**
 * 秒杀活动
 *
 * @author lizhifu
 * @since 2023/7/15
 */
public interface SeckillActivityDomainService {
    /**
     * 根据活动id获取活动信息
     * @param activityId 活动id
     * @return 活动信息
     */
    SeckillActivity getSeckillActivityById(Long activityId);
}
