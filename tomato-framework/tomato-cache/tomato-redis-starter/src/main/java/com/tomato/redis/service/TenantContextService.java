package com.tomato.redis.service;

/**
 * 获取租户信息
 *
 * @author lizhifu
 * @since 2023/7/16
 */
public interface TenantContextService {

	/**
	 * 获取租户信息
	 * @param key key
	 * @return 租户信息
	 */
	String getTenant(String key);

}
