package com.tomato.seckill.constant;

/**
 * redis key 常量
 *
 * @author lizhifu
 * @since 2023/3/17
 */
public class CacheConstant {

	/**
	 * 秒杀活动key
	 */
	public static final String SECKILL_ACTIVITY_CACHE_KEY = "SECKILL_ACTIVITY_CACHE_KEY";

	/**
	 * 秒杀活动key过期时间
	 */
	public static final Long SECKILL_ACTIVITY_CACHE_KEY_TIME_OUT = 5 * 60L;

	/**
	 * 秒杀活动分布式锁key
	 */
	public static final String SECKILL_ACTIVITY_UPDATE_CACHE_LOCK_KEY = "SECKILL_ACTIVITY_UPDATE_CACHE_LOCK_KEY";

	/**
	 * 秒杀活动商品基本信息key
	 */
	public static final String SECKILL_GOODS_INFO = "SECKILL:GOODS:INFO:";

	/**
	 * 秒杀活动商品库存信息key
	 */
	public static final String SECKILL_GOODS = "SECKILL:GOODS:";

	/**
	 * 秒杀活动商品库存信息key
	 */
	public static final String SECKILL_GOODS_SECKILL = "SECKILL:GOODS:SECKILL:";

	/**
	 * 商品购买用户key
	 */
	public static final String SECKILL_GOODS_SECKILL_USER = "SECKILL:GOODS:SECKILL:USER:";

}
