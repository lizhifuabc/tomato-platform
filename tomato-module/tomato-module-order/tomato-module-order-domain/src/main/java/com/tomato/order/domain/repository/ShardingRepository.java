package com.tomato.order.domain.repository;

/**
 * 分库分表
 *
 * @author lizhifu
 * @since 2023/8/5
 */
public interface ShardingRepository {

	/**
	 * 获取分库
	 * @param merchantNoSpilt 商户号
	 * @return 分库
	 */
	String getShardingDb(String merchantNoSpilt);

	/**
	 * 获取分表
	 * @param merchantNoSpilt 商户号
	 * @return 分表
	 */
	String getShardingTable(String merchantNoSpilt);

	/**
	 * 获取分库
	 * @param orderNo 订单号
	 * @return 分库
	 */
	String getShardingDbByOrderNo(String orderNo);

	/**
	 * 获取分表
	 * @param orderNo 订单号
	 * @return 分表
	 */
	String getShardingTableByOrderNo(String orderNo);

}
