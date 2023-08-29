package com.tomato.seckill.goods.infrastructure.repository;

import com.tomato.seckill.goods.domain.entity.SeckillGoods;
import com.tomato.seckill.goods.domain.repository.SeckillGoodsRepository;
import com.tomato.seckill.goods.infrastructure.mapper.SeckillGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2023/7/14
 */
@Component
public class SeckillGoodsRepositoryImpl implements SeckillGoodsRepository {

	@Autowired
	private SeckillGoodsMapper seckillGoodsMapper;

	@Override
	public int saveSeckillGoods(SeckillGoods seckillGoods) {
		return seckillGoodsMapper.insert(seckillGoods);
	}

}
