package com.tomato.goods.seckill.dao;

import com.tomato.goods.entity.SeckillGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 秒杀活动商品{@link SeckillGoodsEntity}
 *
 * @author lizhifu
 * @date 2022/12/10
 */
@Mapper
public interface SeckillGoodsDao {
    /**
     * 更新秒杀剩余量
     * @return
     */
    public int updateSkillRemaining(@Param("id") Long id,@Param("version") Integer version);
}
