package com.tomato.seckill.goods.domain.service.impl;

import com.tomato.seckill.goods.domain.entity.SeckillGoods;
import com.tomato.seckill.goods.domain.repository.SeckillGoodsRepository;
import com.tomato.seckill.goods.domain.service.SeckillGoodsDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 秒杀活动商品
 *
 * @author lizhifu
 * @since 2023/7/14
 */
@Service
@Slf4j
public class SeckillGoodsDomainServiceImpl implements SeckillGoodsDomainService {

	@Autowired
	private SeckillGoodsRepository seckillGoodsRepository;

	@Override
	public void saveSeckillGoods(SeckillGoods seckillGoods) {
		// 保存商品到数据库
		seckillGoodsRepository.saveSeckillGoods(seckillGoods);
		// TODO 秒杀商品事件发布
	}

}
