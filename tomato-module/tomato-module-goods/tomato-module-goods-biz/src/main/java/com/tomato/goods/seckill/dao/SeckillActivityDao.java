package com.tomato.goods.seckill.dao;

import com.tomato.goods.domain.entity.SeckillActivityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

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
    public void insert(@Param("seckillActivity") SeckillActivityEntity seckillActivity);

    /**
     * 查询即将开始的活动
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return id 集合
     */
    public List<Long> selectByTime(@Param("startTime")LocalDateTime startTime,@Param("endTime")LocalDateTime endTime);
}
