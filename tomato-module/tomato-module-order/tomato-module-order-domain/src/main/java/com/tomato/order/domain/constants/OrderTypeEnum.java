package com.tomato.order.domain.constants;

import com.tomato.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单类型枚举
 *
 * @author lizhifu
 * @since 2022/12/2
 */
@AllArgsConstructor
@Getter
public enum OrderTypeEnum implements BaseEnum<Integer> {

	/**
	 * 1 标准版
	 */
	STANDARD(1, "标准版"),

	/**
	 * 2 专业版
	 */
	PROFESSIONAL(2, "专业版"),;

	private final Integer value;

	private final String desc;

}
