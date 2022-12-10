package com.tomato.goods.seckill.dao;

import com.tomato.goods.entity.SeckillUserEntity;
import com.tomato.goods.seckill.domain.bo.UpdateSeckillRemainingBO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户参与活动记录 {@link SeckillUserEntity}
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Mapper
public interface SeckillUserDao {
    /**
     * 更新秒杀剩余量
     * @return
     */
    public int updateSkillRemaining(UpdateSeckillRemainingBO updateSkillRemainingBO);

    /**
     * 插入
     * @param seckillUserEntity
     * @return
     */
    public void insert(SeckillUserEntity seckillUserEntity);
}
