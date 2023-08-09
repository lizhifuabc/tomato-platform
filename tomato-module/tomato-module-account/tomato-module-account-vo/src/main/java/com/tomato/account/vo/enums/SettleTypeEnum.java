package com.tomato.account.vo.enums;

import com.tomato.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结算方式枚举
 * @author lizhifu
 */
@AllArgsConstructor
@Getter
public enum SettleTypeEnum implements BaseEnum<String> {
	/**
	 * 自动结算（定期结算）
	 */
	AUTO_SETTLEMENT("AUTO_SETTLEMENT","自动结算（定期结算）"),
	/**
	 * 自助结算（不定期结算）
	 */
	SELF_HELP_SETTLEMENT("SELF_HELP_SETTLEMENT","自助结算（不定期结算）"),
	;

	private final String value;

	private final String desc;
}
