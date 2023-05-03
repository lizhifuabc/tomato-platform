package com.tomato.sys.domain.service;

import com.tomato.jpa.domain.service.AbstractService;
import com.tomato.jpa.domain.service.BaseReadableService;
import com.tomato.jpa.domain.service.BaseWriteableService;
import com.tomato.sys.domain.entity.SysTenant;

import java.util.Optional;

/**
 * 多租户
 *
 * @author lizhifu
 * @since 2023/5/3
 */
public interface SysTenantService extends BaseReadableService<SysTenant, String>, BaseWriteableService<SysTenant, String> {
    /**
     * 根据租户ID查询
     * @param tenantId 租户ID
     * @return 租户信息
     */
    Optional<SysTenant> findByTenantId(String tenantId);
}
