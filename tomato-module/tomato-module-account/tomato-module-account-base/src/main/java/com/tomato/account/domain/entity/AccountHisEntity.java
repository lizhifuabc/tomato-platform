package com.tomato.account.domain.entity;

import com.tomato.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账户历史表
 *
 * @author lizhifu
 * @since 2022/6/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccountHisEntity extends BaseEntity {

	/**
	 * 账户历史流水顺序号
	 */
	private Long accountHisSerial;

	/**
	 * 账户编号
	 */
	private String accountNo;

	/**
	 * 商户编号
	 */
	private String merchantNo;

	/**
	 * 商户订单号(同一个商户唯一)
	 */
	private String merchantOrderNo;

	/**
	 * 系统流水号（唯一）
	 */
	private String sysNo;

	/**
	 * 发生前余额
	 */
	private BigDecimal beforeBalance;

	/**
	 * 发生后余额
	 */
	private BigDecimal afterBalance;

	/**
	 * 发生金额
	 */
	private BigDecimal amount;

	/**
	 * 手续费
	 */
	private BigDecimal amountFree;

	/**
	 * 费率快照
	 */
	private BigDecimal amountRate;

	/**
	 * 账户历史类型
	 */
	private String accountHisType;

	/**
	 * 完成时间
	 */
	private LocalDateTime completeTime;

	/**
	 * 账户历史状态
	 */
	private String accountHisStatus;

	/**
	 * 备注
	 */
	private String remark;

}
