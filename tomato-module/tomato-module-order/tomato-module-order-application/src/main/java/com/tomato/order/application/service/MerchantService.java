package com.tomato.order.application.service;

import com.tomato.order.domain.domain.entity.MerchantEntity;
import com.tomato.order.domain.repository.MerchantRepository;
import org.springframework.stereotype.Service;

/**
 * 商户服务
 *
 * @author lizhifu
 * @since 2023/8/2
 */
@Service
public class MerchantService {

	private final MerchantRepository merchantRepository;

	public MerchantService(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}

	public MerchantEntity merchant(MerchantEntity merchantEntity) {
		return merchantRepository.merchant(merchantEntity);
	}

	public String merchantKey(String merchantNo) {
		return merchantRepository.merchantKey(merchantNo);
	}

}
