package com.tomato.module.common.enums;

import com.tomato.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态
 *
 * @author lizhifu
 * @since 2024/6/10
 */
@AllArgsConstructor
@Getter
public enum PayStatusEnum implements BaseEnum<String> {
	/**
	 * 初始化
	 */
	INIT("INIT", "初始化"),
	/**
	 * 支付中
	 */
	DEAL("DEAL", "支付中"),
	/**
	 * 成功
	 */
	SUCCESS("SUCCESS", "成功"),
	/**
	 * 预授权回执成功
	 */
	AUTH_SUCCESS("AUTH_SUCCESS", "预授权回执成功"),
	/**
	 * 请款发起，部分成功
	 */
	CAPTURING("CAPTURING", "请款发起"),
	/**
	 * 关闭：超时未支付，预授权取消（失败），全额撤销（失败）
	 */
	CLOSE("CLOSE", "关闭")
	;
	private final String value;

	private final String desc;
}
