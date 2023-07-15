package com.tomato.seckill.goods.infrastructure.mapper;

import com.tomato.mybatis.mapper.BaseMapper;
import com.tomato.seckill.goods.domain.entity.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品接口
 *
 * @author lizhifu
 * @since 2023/7/14
 */
@Mapper
public interface SeckillGoodsMapper extends BaseMapper<SeckillGoods,Long> {
}
