package com.tomato.account.enums;

import com.tomato.domain.type.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结算周期类型枚举
 * @author lizhifu
 */
@AllArgsConstructor
@Getter
public enum CycleTypeEnum implements BaseEnum {
	/**
	 * 月结
	 */
	MONTH("MONTH","月结"),
	/**
	 * 周结
	 */
	WEEK("WEEK","周结"),
	;

	private final String value;

	private final String desc;
}
