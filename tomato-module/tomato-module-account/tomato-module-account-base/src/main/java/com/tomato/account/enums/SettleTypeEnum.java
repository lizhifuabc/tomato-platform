package com.tomato.account.enums;

/**
 * @Title SettleType.java
 * @Description 结算类型枚举
 * @author 崔博
 * @date 2015年11月5日 下午4:18:49
 * @version V1.0
 */
public enum SettleTypeEnum {
	/**
	 * 自动结算（定期结算）
	 */
	AUTO_SETTLEMENT,
	/**
	 * 实时结算（不定期结算）
	 */
	REALTIME_SETTLEMENT,
	/**
	 * 自助结算（不定期结算）
	 */
	SELF_HELP_SETTLEMENT;
}
