package com.tomato.sys.infrastructure.impl;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.sys.domain.entity.SysTenant;
import com.tomato.sys.domain.service.SysTenantService;
import com.tomato.sys.infrastructure.repository.SysTenantJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 多租户接口
 *
 * @author lizhifu
 * @since 2023/5/3
 */
@Service
public class SysTenantServiceImpl implements SysTenantService {

	private final SysTenantJpaRepository sysTenantJpaRepository;

	public SysTenantServiceImpl(SysTenantJpaRepository sysTenantJpaRepository) {
		this.sysTenantJpaRepository = sysTenantJpaRepository;
	}

	@Override
	public Optional<SysTenant> findByTenantId(Long tenantId) {
		return Optional.ofNullable(sysTenantJpaRepository.findByTenantId(tenantId));
	}

	@Override
	public BaseJpaRepository<SysTenant, Long> getRepository() {
		return this.sysTenantJpaRepository;
	}

}
