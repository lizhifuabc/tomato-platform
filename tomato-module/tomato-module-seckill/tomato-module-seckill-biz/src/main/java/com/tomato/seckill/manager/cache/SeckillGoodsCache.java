package com.tomato.seckill.manager.cache;

import com.tomato.seckill.constant.CacheConstant;
import com.tomato.seckill.dao.SeckillGoodsDao;
import com.tomato.seckill.domain.entity.SeckillGoodsEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 使用 Redis Hash 数据结构实现秒杀商品缓存
 *
 * @author lizhifu
 * @since 2023/3/24
 */
@Service
@Slf4j
public class SeckillGoodsCache {

	private final StringRedisTemplate stringRedisTemplate;

	private final SeckillGoodsDao seckillGoodsDao;

	public SeckillGoodsCache(StringRedisTemplate stringRedisTemplate, SeckillGoodsDao seckillGoodsDao) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.seckillGoodsDao = seckillGoodsDao;
	}

	/**
	 * 缓存秒杀单个商品信息 {@link com.tomato.seckill.domain.entity.SeckillGoodsEntity
	 * @param seckillGoodsId 秒杀活动商品ID
	 */
	public void saveSeckillGoods(Long seckillGoodsId) {
		SeckillGoodsEntity seckillGoodsEntity = seckillGoodsDao.selectById(seckillGoodsId);
		String redisKey = CacheConstant.SECKILL_GOODS_SECKILL + seckillGoodsEntity.getId();
		try {
			// 删除 key
			stringRedisTemplate.delete(redisKey);
			HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
			// 放入缓存
			Map<String, String> describe = BeanUtils.describe(seckillGoodsEntity);
			describe.entrySet().removeIf(entry -> entry.getValue() == null || "".equals(entry.getValue()));
			hashOps.putAll(redisKey, describe);
		}
		catch (Exception ignore) {
			log.error("秒杀商品redisKey:{}缓存异常", redisKey, ignore);
		}
	}

	/**
	 * 秒杀商品查询
	 * @param seckillGoodsId 秒杀活动商品ID
	 * {@link com.tomato.seckill.domain.entity.SeckillGoodsEntity }
	 * @return 秒杀商品信息
	 */
	public SeckillGoodsEntity getSeckillGoodsById(Long seckillGoodsId) {
		String redisKey = CacheConstant.SECKILL_GOODS_SECKILL + seckillGoodsId;
		HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
		Map<String, String> entries = hashOps.entries(redisKey);
		SeckillGoodsEntity seckillGoodsEntity = new SeckillGoodsEntity();
		try {
			entries.entrySet().removeIf(entry -> entry.getValue() == null || "".equals(entry.getValue()));
			BeanUtils.populate(seckillGoodsEntity, entries);
		}
		catch (Exception ignore) {
			log.error("秒杀商品redisKey:{}缓存异常", redisKey, ignore);
			return null;
		}
		return seckillGoodsEntity;
	}

}