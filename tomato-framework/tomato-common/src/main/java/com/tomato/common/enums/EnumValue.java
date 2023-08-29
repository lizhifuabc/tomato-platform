package com.tomato.common.enums;

/**
 * 枚举值接口
 *
 * @author lizhifu
 * @param <T>
 */
public interface EnumValue<T> {

	/**
	 * 获取枚举自定义值
	 * @return 自定义枚举值
	 */
	T getValue();

}
