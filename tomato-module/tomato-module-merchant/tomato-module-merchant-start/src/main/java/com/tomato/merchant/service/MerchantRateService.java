package com.tomato.merchant.service;

import com.tomato.common.enums.YesNoTypeEnum;
import com.tomato.common.exception.BusinessException;
import com.tomato.merchant.domain.dto.MerchantRateDTO;
import com.tomato.merchant.domain.entity.MerchantRate;
import com.tomato.merchant.domain.req.MerchantRateReq;
import com.tomato.merchant.repository.MerchantInfoRepository;
import com.tomato.merchant.repository.MerchantRateRepository;
import com.tomato.web.core.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 商户费率
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@Service
public class MerchantRateService {

	private final MerchantRateRepository merchantRateRepository;

	private final MerchantInfoRepository merchantInfoRepository;

	public MerchantRateService(MerchantRateRepository merchantRateRepository,
			MerchantInfoRepository merchantInfoRepository) {
		this.merchantRateRepository = merchantRateRepository;
		this.merchantInfoRepository = merchantInfoRepository;
	}

	public MerchantRateDTO trade(String merchantNo, Integer payType) {
		MerchantRateDTO merchantRateDTO = new MerchantRateDTO();
		MerchantRate merchantRate = merchantRateRepository.findByMerchantNoAndPayType(merchantNo, payType)
			.orElseThrow(() -> new BusinessException("商户费率不存在"));
		// 判断交易费率状态
		if (merchantRate.getTradeStatus().equals(YesNoTypeEnum.YES.getValue())) {
			throw new BusinessException("交易费率已关闭");
		}
		// 交易费率
		merchantRateDTO.setTradeRate(merchantRate.getTradeRate());
		// 将来交易费率生效时间已经生效
		Optional.ofNullable(merchantRate.getTradeRateEffectiveTime())
			.filter(effectiveTime -> effectiveTime.isBefore(LocalDateTime.now()))
			.ifPresent(effectiveTime -> {
				merchantRateDTO.setTradeRate(merchantRate.getFutureTradeRate());
			});
		// 判断分账费率状态
		if (merchantRate.getSplitStatus().equals(YesNoTypeEnum.YES.getValue())) {
			return merchantRateDTO;
		}
		// 分账费率
		merchantRateDTO.setSplitRate(merchantRate.getSplitRate());
		// 将来分账费率生效时间已经生效
		Optional.ofNullable(merchantRate.getSplitRateEffectiveTime())
			.filter(effectiveTime -> effectiveTime.isBefore(LocalDateTime.now()))
			.ifPresent(effectiveTime -> {
				merchantRateDTO.setSplitRate(merchantRate.getFutureSplitRate());
			});
		return merchantRateDTO;
	}

	public void rate(MerchantRateReq merchantRateReq) {
		merchantInfoRepository.findByMerchantNo(merchantRateReq.getMerchantNo())
			.orElseThrow(() -> new BusinessException("商户不存在"));
		MerchantRate merchantRate = BeanUtil.copy(merchantRateReq, MerchantRate.class);
		merchantRateRepository.save(merchantRate);
	}

}
