package com.tomato.account.domain.entity;

import com.tomato.common.entity.BaseEntity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 每日待结算汇总
 *
 * @author lizhifu
 * @since 2022/7/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "t_account_daily_collect")
public class AccountDailyCollectEntity extends BaseEntity {

	/**
	 * 账户编号
	 */
	private String accountNo;

	/**
	 * 商户编号
	 */
	private String merchantNo;

	/**
	 * 汇总日期
	 */
	private LocalDate collectDate;

	/**
	 * 交易总金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 交易总笔数
	 */
	private Long totalCount;

	/**
	 * 账户历史类型
	 */
	private String accountHisType;

}
