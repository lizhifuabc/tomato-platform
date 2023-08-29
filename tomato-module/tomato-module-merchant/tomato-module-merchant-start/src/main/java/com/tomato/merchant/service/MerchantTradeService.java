package com.tomato.merchant.service;

import com.tomato.common.exception.BusinessException;
import com.tomato.merchant.domain.dto.MerchantRateDTO;
import com.tomato.merchant.domain.entity.MerchantConfig;
import com.tomato.merchant.domain.entity.MerchantInfo;
import com.tomato.merchant.domain.req.MerchantTradReq;
import com.tomato.merchant.domain.resp.MerchantTradResp;
import com.tomato.merchant.repository.MerchantConfigRepository;
import com.tomato.merchant.repository.MerchantInfoRepository;
import org.springframework.stereotype.Service;

/**
 * 商户交易
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@Service
public class MerchantTradeService {

	private final MerchantRateService merchantRateService;

	private final MerchantInfoRepository merchantInfoRepository;

	private final MerchantConfigRepository merchantConfigRepository;

	public MerchantTradeService(MerchantRateService merchantRateService, MerchantInfoRepository merchantInfoRepository,
			MerchantConfigRepository merchantConfigRepository) {
		this.merchantRateService = merchantRateService;
		this.merchantInfoRepository = merchantInfoRepository;
		this.merchantConfigRepository = merchantConfigRepository;
	}

	public MerchantTradResp trade(MerchantTradReq merchantTradReq) {
		// 商户信息
		MerchantInfo merchantInfo = merchantInfoRepository.findByMerchantNo(merchantTradReq.getMerchantNo())
			.orElseThrow(() -> new BusinessException("商户不存在"));
		// 商户费率
		MerchantRateDTO merchantRateDTO = merchantRateService.trade(merchantTradReq.getMerchantNo(),
				merchantTradReq.getPayType());
		// 商户配置
		MerchantConfig merchantConfig = merchantConfigRepository.findByMerchantNo(merchantTradReq.getMerchantNo())
			.orElseThrow(() -> new BusinessException("商户配置不存在"));
		return MerchantTradResp.builder()
			.merchantNo(merchantInfo.getMerchantNo())
			.merchantName(merchantInfo.getMerchantName())
			.merchantKey(merchantConfig.getMerchantKey())
			.trxRate(merchantRateDTO.getTradeRate())
			.splitRate(merchantRateDTO.getSplitRate())
			.build();
	}

}
