package com.tomato.merchant.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户费率
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@Data
public class MerchantRateDTO {

	/**
	 * 交易费率
	 */
	private BigDecimal tradeRate;

	/**
	 * 分账费率
	 */
	private BigDecimal splitRate;

}
