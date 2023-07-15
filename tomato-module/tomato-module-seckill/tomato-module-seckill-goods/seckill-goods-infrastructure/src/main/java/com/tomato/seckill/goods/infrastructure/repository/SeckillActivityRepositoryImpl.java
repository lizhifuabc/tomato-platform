package com.tomato.seckill.goods.infrastructure.repository;

import com.tomato.seckill.exception.SeckillException;
import com.tomato.seckill.goods.domain.entity.SeckillActivity;
import com.tomato.seckill.goods.domain.repository.SeckillActivityRepository;
import com.tomato.seckill.goods.infrastructure.mapper.SeckillActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author lizhifu
 * @since 2023/7/15
 */
@Component
public class SeckillActivityRepositoryImpl implements SeckillActivityRepository {
    @Autowired
    private SeckillActivityMapper seckillActivityMapper;
    @Override
    public SeckillActivity getSeckillActivityById(Long activityId) {
        return seckillActivityMapper.selectByPrimaryKey(activityId).orElseThrow(()-> new SeckillException("秒杀活动不存在"));
    }
}
