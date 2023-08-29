package com.tomato.sys.application.context;

/**
 * ThreadLocal，租户信息
 *
 * @author lizhifu
 * @since 2023/6/12
 */
public class TenantContextHolder {

	/**
	 * 设置租户ID
	 * @param tenantId 租户ID
	 */
	public static void setTenantId(final Long tenantId) {

	}

	/**
	 * 获取租户ID
	 * @return 租户ID
	 */
	public static Long getTenantId() {
		return null;
	}

	/**
	 * 清除租户ID
	 */
	public static void clear() {

	}

}
