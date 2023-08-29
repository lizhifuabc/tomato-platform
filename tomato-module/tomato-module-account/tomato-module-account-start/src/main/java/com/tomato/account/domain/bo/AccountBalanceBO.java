package com.tomato.account.domain.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户金额操作
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@Data
public class AccountBalanceBO {

	/**
	 * 账户编号
	 */
	private String accountNo;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 上日账户余额
	 */
	private BigDecimal yesterdayBalance;

	/**
	 * 上一次交易日期
	 */
	private LocalDateTime lastTradTime;

}
