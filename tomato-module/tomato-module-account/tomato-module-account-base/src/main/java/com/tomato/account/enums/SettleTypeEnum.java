package com.tomato.account.enums;

import com.tomato.domain.type.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结算类型枚举
 * @author lizhifu
 */
@AllArgsConstructor
@Getter
public enum SettleTypeEnum implements BaseEnum {
	/**
	 * 自动结算（定期结算）
	 */
	AUTO_SETTLEMENT("AUTO_SETTLEMENT","自动结算（定期结算）"),
	/**
	 * 实时结算（不定期结算）
	 */
	REALTIME_SETTLEMENT("REALTIME_SETTLEMENT","实时结算（不定期结算）"),
	/**
	 * 自助结算（不定期结算）
	 */
	SELF_HELP_SETTLEMENT("SELF_HELP_SETTLEMENT","自助结算（不定期结算）"),
	;

	private final String value;

	private final String desc;
}
