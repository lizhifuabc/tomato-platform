package com.tomato.seckill.dao;

import com.tomato.seckill.domain.entity.SeckillActivityEntity;
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
     * @param seckillActivity 秒杀活动
     */
    void insert(@Param("seckillActivity") SeckillActivityEntity seckillActivity);

    /**
     * 查询即将开始的活动
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return id 集合
     */
    List<Long> selectByTime(@Param("startTime")LocalDateTime startTime,@Param("endTime")LocalDateTime endTime);
    /**
     * 查询即将开始的活动
     * @param start 开始时间
     * @param end 结束时间
     * @return id 集合
     */
    List<Long> selectByStartTime(@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);

    /**
     * 根据 ID 查询
     * @param id ID
     * @return 秒杀活动
     */
    SeckillActivityEntity selectById(@Param("id") Long id);
}
