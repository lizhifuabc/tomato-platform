package com.tomato.redis.service;

/**
 * 默认租户信息
 *
 * @author lizhifu
 * @since 2023/7/16
 */
public class DefaultTenantContextService implements TenantContextService{
    @Override
    public String getTenant(String key) {
        return key;
    }
}
