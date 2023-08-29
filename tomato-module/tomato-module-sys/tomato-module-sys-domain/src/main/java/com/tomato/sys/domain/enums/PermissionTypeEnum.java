package com.tomato.sys.domain.enums;

import com.tomato.common.enums.BaseEnum;

/**
 * 权限类型
 *
 * @author lizhifu
 * @since 2023/6/10
 */
public enum PermissionTypeEnum implements BaseEnum<Integer> {

	/**
	 * 目录
	 */
	CATALOG(1, "目录"),
	/**
	 * 菜单
	 */
	MENU(2, "菜单"),
	/**
	 * 功能点
	 */
	POINTS(3, "功能点");

	private final Integer value;

	private final String desc;

	PermissionTypeEnum(Integer value, String desc) {
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

}
