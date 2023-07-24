package com.tomato.merchant.repository;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.merchant.domain.entity.MerchantRate;

/**
 * 商户费率表
 *
 * @author lizhifu
 * @since 2023/7/24
 */
public interface MerchantRateRepository extends BaseJpaRepository<MerchantRate,Long> {
}
