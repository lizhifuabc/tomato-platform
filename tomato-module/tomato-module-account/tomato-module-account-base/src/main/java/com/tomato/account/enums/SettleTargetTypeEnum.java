package com.tomato.account.enums;

import com.tomato.domain.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结算目标枚举
 * @author lizhifu
 */
@AllArgsConstructor
@Getter
public enum SettleTargetTypeEnum implements BaseEnum<String> {
	/**
	 * 银行卡账户
	 */
	BANK_CARD("BANK_CARD","银行卡账户"),
	;
	
	/**    
	 * 是否需要异步接收打款结果通知
	 * @return    
	*/
	public boolean needAsynchronousRemitNotify(){
		return this.equals(BANK_CARD);
	}
	private final String value;

	private final String desc;
}
