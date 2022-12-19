package com.tomato.goods.seckill.dao;

import com.tomato.goods.domain.entity.SeckillActivityEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动记录
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Mapper
public interface SeckillActivityDao {
    /**
     * 创建
     * @param seckillActivity 秒杀活动记录
     */
    public void insert(SeckillActivityEntity seckillActivity);
}
