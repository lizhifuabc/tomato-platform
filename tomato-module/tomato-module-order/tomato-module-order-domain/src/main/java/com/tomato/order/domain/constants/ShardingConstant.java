package com.tomato.order.domain.constants;

/**
 * 分库分表常量
 *
 * @author lizhifu
 * @since 2023/8/5
 */
public class ShardingConstant {

	public static final String DB_DEFAULT = "DEFAULT";

	public static final String DB_PREFIX = "ds_";

	/**
	 * 商户号分片
	 */
	public static final int MERCHANT_SPILT = 6;

	/**
	 * 分片数
	 */
	public static final int SHARDING_COUNT = 2;

	/**
	 * 商户号后六位
	 * @param merchantNo 商户号
	 * @return 商户号后六位
	 */
	public static String merchantNoSpilt(String merchantNo) {
		// 商户号后六位
		return merchantNo.substring(Math.max(merchantNo.length() - ShardingConstant.MERCHANT_SPILT, 0));
	}

	/**
	 * 订单号后六位
	 * @param orderNo 商户号
	 * @return 商户号后六位
	 */
	public static String orderNoSpilt(String orderNo) {
		// 订单号后六位
		return orderNo.substring(Math.max(orderNo.length() - ShardingConstant.MERCHANT_SPILT, 0));
	}

	/**
	 * hash分片
	 * @param shardingValue 分片值
	 * @return 分片值
	 */
	public static long hashShardingValue(final Comparable<?> shardingValue) {
		return Math.abs((long) shardingValue.hashCode());
	}

}
