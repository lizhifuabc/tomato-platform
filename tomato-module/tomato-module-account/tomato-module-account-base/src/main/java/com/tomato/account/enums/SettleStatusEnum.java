package com.tomato.account.enums;

import com.tomato.domain.type.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结算状态枚举
 * @author lizhifu
 */
@AllArgsConstructor
@Getter
public enum SettleStatusEnum implements BaseEnum {
	/**
	 * 	可结算
	 */
	SETTLE("SETTLE","可结算"),
	/**
	 * 不可结算
	 */
	SETTLE_UN("SETTLE_UN","不可结算"),
	/**
	 * 结算中
	 */
	SETTLING("SETTLING","结算中"),
	;
	private final String value;

	private final String desc;
}
