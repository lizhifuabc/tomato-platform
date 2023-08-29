package com.tomato.merchant.domain.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商户交易请求
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@Data
public class MerchantTradReq {

	/**
	 * 商户号
	 */
	@NotBlank(message = "商户号不能为空")
	private String merchantNo;

	/**
	 * 支付方式
	 */
	@NotNull
	private Integer payType;

}
