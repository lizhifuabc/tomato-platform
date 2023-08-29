package com.tomato.seckill.goods.infrastructure.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.seckill.goods.domain.entity.SeckillActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动
 *
 * @author lizhifu
 * @since 2023/7/15
 */
@Mapper
public interface SeckillActivityMapper extends BaseMapper<SeckillActivity, Long> {

}
