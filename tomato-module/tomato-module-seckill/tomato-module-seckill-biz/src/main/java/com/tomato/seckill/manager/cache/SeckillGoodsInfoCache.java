package com.tomato.seckill.manager.cache;

import com.tomato.common.domain.resp.Resp;
import com.tomato.goods.domain.resp.GoodsInfoResp;
import com.tomato.goods.feign.RemoteGoodsService;
import com.tomato.seckill.constant.CacheConstant;
import com.tomato.seckill.dao.SeckillGoodsDao;
import com.tomato.seckill.domain.entity.SeckillGoodsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 秒杀活动商品缓存
 *
 * @author lizhifu
 * @since 2023/3/22
 */
@Service
@Slf4j
public class SeckillGoodsInfoCache {

	private final RemoteGoodsService remoteGoodsService;

	private final SeckillGoodsDao seckillGoodsDao;

	private final StringRedisTemplate stringRedisTemplate;

	public SeckillGoodsInfoCache(RemoteGoodsService remoteGoodsService, SeckillGoodsDao seckillGoodsDao,
			StringRedisTemplate stringRedisTemplate) {
		this.remoteGoodsService = remoteGoodsService;
		this.seckillGoodsDao = seckillGoodsDao;
		this.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 缓存秒杀单个商品信息 {@link com.tomato.seckill.domain.entity.SeckillGoodsEntity}
	 * @param seckillGoodsId 秒杀活动商品ID
	 */
	public void saveSeckillGoodsInfo(Long seckillGoodsId) {
		String redisKey = CacheConstant.SECKILL_GOODS_INFO + seckillGoodsId;
		SeckillGoodsEntity seckillGoodsEntity = seckillGoodsDao.selectById(seckillGoodsId);
		try {
			// 删除 key
			stringRedisTemplate.delete(redisKey);

			Resp<GoodsInfoResp> resp = remoteGoodsService.queryGoodsInfo(seckillGoodsEntity.getGoodsId());
			if (!resp.isSuccess()) {
				log.error("秒杀商品基本信息查询失败,seckillGoodsId:{},resp:{}", seckillGoodsId, resp);
				return;
			}

			HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
			// 放入缓存
			Map<String, String> describe = BeanUtils.describe(resp.getData());
			describe.entrySet().removeIf(entry -> entry.getValue() == null || "".equals(entry.getValue()));
			hashOps.putAll(redisKey, describe);
		}
		catch (Exception ignore) {
			log.error("秒杀商品基本信息redisKey:{}缓存异常", redisKey, ignore);
		}
	}

	/**
	 * 秒杀商品查询
	 * @param seckillGoodsId 秒杀活动商品ID
	 * {@link com.tomato.seckill.domain.entity.SeckillGoodsEntity}
	 * @return 秒杀商品信息 {@link GoodsInfoResp }
	 */
	public GoodsInfoResp getSeckillGoodsById(Long seckillGoodsId) {
		String redisKey = CacheConstant.SECKILL_GOODS_INFO + seckillGoodsId;
		HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
		Map<String, String> entries = hashOps.entries(redisKey);
		GoodsInfoResp goodsInfoResp = new GoodsInfoResp();
		try {
			entries.entrySet().removeIf(entry -> entry.getValue() == null || "".equals(entry.getValue()));
			BeanUtils.populate(goodsInfoResp, entries);
		}
		catch (Exception ignore) {
			log.error("秒杀商品基本信息redisKey:{}缓存异常", redisKey, ignore);
			return null;
		}
		return goodsInfoResp;
	}

}