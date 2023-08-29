package com.tomato.merchant.repository;

import com.tomato.jpa.domain.repository.BaseJpaRepository;
import com.tomato.merchant.domain.entity.MerchantConfig;

import java.util.Optional;

/**
 * 商户交易配置
 *
 * @author lizhifu
 * @since 2023/7/24
 */
public interface MerchantConfigRepository extends BaseJpaRepository<MerchantConfig, Long> {

	/**
	 * 根据商户号查询
	 * @param merchantNo 商户号
	 * @return 商户配置
	 */
	Optional<MerchantConfig> findByMerchantNo(String merchantNo);

}
