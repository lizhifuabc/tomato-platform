package com.tomato.common.holder;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * ThreadLocal，租户信息
 *
 * @author lizhifu
 * @since 2024/1/12
 */
public class TenantContextHolder {
	private static final ThreadLocal<Long> CURRENT_CONTEXT = new TransmittableThreadLocal<>();

	public static Long getTenantId() {
		return CURRENT_CONTEXT.get();
	}

	public static void setTenantId(final Long tenantId) {
		CURRENT_CONTEXT.set(tenantId);
	}

	public static void clear() {
		CURRENT_CONTEXT.remove();
	}
}
