package com.tomato.merchant.repository;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.merchant.domain.entity.MerchantRate;

import java.util.Optional;

/**
 * 商户费率表
 *
 * @author lizhifu
 * @since 2023/7/24
 */
public interface MerchantRateRepository extends BaseJpaRepository<MerchantRate, Long> {

	/**
	 * 根据商户号查询
	 * @param merchantNo 商户号
	 * @param payType 支付类型
	 * @return 商户费率
	 */
	Optional<MerchantRate> findByMerchantNoAndPayType(String merchantNo, Integer payType);

}
