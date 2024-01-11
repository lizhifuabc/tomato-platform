package com.tomato.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 金额转换枚举
 *
 * @author lizhifu
 * @since 2024/1/4
 */
@Getter
@AllArgsConstructor
public enum UnitConvertEnum implements BaseEnum<String> {

	/**
	 * 金额转换枚举
	 */
	R("R", "转换为2 位小数，四舍五入"),


	B("B", "转换为万元"),


	PERCENTAGE("PERCENTAGE", "转换为百分"),


	PERMIL("PERMIL", "转换为千分"),
	;

	private final String value;

	private final String desc;

}
