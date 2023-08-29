package com.tomato.account.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 结算内部账户退款
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@Data
public class AccountRefundBO {

	/**
	 * 商户编号
	 */
	@NotBlank(message = "商户编号不能为空")
	private String merchantNo;

	/**
	 * 原始第三方流水号不能为空
	 */
	@NotBlank(message = "原始第三方流水号不能为空")
	private String orgThirdNo;

}
