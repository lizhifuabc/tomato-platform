package com.tomato.sys.infrastructure.repository;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.sys.domain.entity.SysTenant;

/**
 * 多租户
 *
 * @author lizhifu
 * @since 2023/5/3
 */
public interface SysTenantJpaRepository extends BaseJpaRepository<SysTenant, String> {
    /**
     * 根据租户ID查询
     * @param tenantId 租户ID
     * @return 租户信息
     */
    SysTenant findByTenantId(String tenantId);
}
