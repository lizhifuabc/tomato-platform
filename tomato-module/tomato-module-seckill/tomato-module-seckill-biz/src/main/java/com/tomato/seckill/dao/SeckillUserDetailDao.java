package com.tomato.seckill.dao;

import com.tomato.seckill.domain.entity.SeckillUserDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户参与活动明细 {@link SeckillUserDetailEntity}
 *
 * @author lizhifu
 * @since 2022/12/19
 */
@Mapper
public interface SeckillUserDetailDao {
    /**
     * 插入
     * @param seckillUserDetailEntity 用户参与活动明细
     */
    void insert(@Param("seckillUserDetailEntity") SeckillUserDetailEntity seckillUserDetailEntity);
}
