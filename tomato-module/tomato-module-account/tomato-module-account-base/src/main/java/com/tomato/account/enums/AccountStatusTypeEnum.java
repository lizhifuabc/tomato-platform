package com.tomato.account.enums;

import com.tomato.domain.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户状态枚举
 * @author lizhifu
 */
@AllArgsConstructor
@Getter
public enum AccountStatusTypeEnum implements BaseEnum<String> {

	/**
	 * 活动中
	 */
	ACCOUNT_AVAILABLE("ACCOUNT_AVAILABLE","活动中"),

	/**
	 * 已注销
	 */
	ACCOUNT_CANCELLED("ACCOUNT_CANCELLED","已注销"),
	
	/**
	 * 冻结止收
	 */
	ACCOUNT_FREEZE_CREDIT("ACCOUNT_FREEZE_CREDIT","冻结止收"),
	
	/**
	 * 冻结止付
	 */
	ACCOUNT_FREEZE_DEBIT("ACCOUNT_FREEZE_DEBIT","冻结止付"),
	
	/**
	 * 完全冻结
	 */
	ACCOUNT_FROZEN("ACCOUNT_FROZEN","完全冻结");
	private final String value;

	private final String desc;
}
