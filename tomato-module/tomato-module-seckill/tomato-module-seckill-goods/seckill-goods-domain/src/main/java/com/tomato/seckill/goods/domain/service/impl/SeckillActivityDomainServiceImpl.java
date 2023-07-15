package com.tomato.seckill.goods.domain.service.impl;

import com.tomato.seckill.goods.domain.entity.SeckillActivity;
import com.tomato.seckill.goods.domain.repository.SeckillActivityRepository;
import com.tomato.seckill.goods.domain.service.SeckillActivityDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 秒杀活动
 *
 * @author lizhifu
 * @since 2023/7/15
 */
@Service
@Slf4j
public class SeckillActivityDomainServiceImpl implements SeckillActivityDomainService {
    @Autowired
    private SeckillActivityRepository seckillActivityRepository;
    @Override
    public SeckillActivity getSeckillActivityById(Long activityId) {
        return seckillActivityRepository.getSeckillActivityById(activityId);
    }
}
