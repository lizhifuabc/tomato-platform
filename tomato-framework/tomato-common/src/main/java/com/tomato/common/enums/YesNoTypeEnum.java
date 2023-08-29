package com.tomato.common.enums;

/**
 * 是否枚举 0-否, 1-是
 *
 * @author lizhifu
 * @since 2022/12/2
 */
public enum YesNoTypeEnum implements BaseEnum<Integer> {

	/**
	 * 1 正确，是的，有，是
	 */
	YES(1, "正确，是的，有"),

	/**
	 * 0 错误，不是的，没有，否
	 */
	NO(0, "错误，不是的，没有"),;

	YesNoTypeEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	private final Integer value;

	private final String desc;

}
