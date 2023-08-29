package com.tomato.account.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 账户手续费配置
 *
 * @author lizhifu
 * @since 2023/4/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountRateEntity extends BaseEntity {

	/**
	 * 账户编号
	 */
	private String accountNo;

	/**
	 * 商户编号
	 */
	private String merchantNo;

	/**
	 * 费率
	 */
	private BigDecimal rate;

	/**
	 * 费率类型
	 */
	private String rateType;

}
