package com.tomato.goods.seckill.dao;

import com.tomato.goods.domain.entity.SeckillUserEntity;
import com.tomato.goods.seckill.domain.bo.UpdateSeckillRemainingBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    int updateSkillRemaining(UpdateSeckillRemainingBO updateSkillRemainingBO);

    /**
     * 插入
     * @param seckillUserEntity
     * @return ID
     */
    Long insert(SeckillUserEntity seckillUserEntity);

    /**
     * 查询
     * @param userId 用户id
     * @param seckillGoodsId 秒杀活动商品记录id
     * @return
     */
    SeckillUserEntity selectByUserIdSeckillGoodsId(@Param("userId") Long userId,@Param("seckillGoodsId") Long seckillGoodsId);
}
