package com.tomato.account.enums;

import com.tomato.domain.type.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 结算周期类型枚举
 * @author lizhifu
 */
@AllArgsConstructor
@Getter
public enum CycleTypeEnum implements BaseEnum {
	/**
	 * 月结:每个月几号结算
	 * 1-30
	 */
	MONTH("MONTH","月结"),
	/**
	 * 月结:每个月几号结算，工作日，顺延一天，默认周末休息
	 * 1-30
	 */
	MONTH_WORK("MONTH_WORK","月结工作日"),
	/**
	 * 周结：每周几结算
	 * 1-7
	 */
	WEEK("WEEK","周结"),
	/**
	 * 周结：每周几结算,工作日，顺延一天，默认周末休息
	 * 1-7
	 */
	WEEK_WORK("WEEK_WORK","周结工作日"),
	;

	private final String value;

	private final String desc;

	public static CycleTypeEnum fromValue(String value){
		return Arrays.stream(CycleTypeEnum.values()).filter(cycleTypeEnum -> cycleTypeEnum.getValue().equals(value)).findFirst().orElse(null);
	}
}
