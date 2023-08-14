package com.tomato.account.vo.enums;

import com.tomato.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户历史类型
 *
 * @author lizhifu
 * @since  2022/7/14
 */
@AllArgsConstructor
@Getter
public enum AccountHisTypeEnum implements BaseEnum<String> {
    /**
     * 结算，减钱
     */
    SETTLEMENT("SETTLEMENT", "结算"),
    /**
     * 交易，加钱
     */
    TRAD("TRAD", "交易"),
    /**
     * 退款，加钱
     */
    REFUND("REFUND", "退款"),
    /**
     * 提现（默认提现在风内），减钱
     */
    CASH("CASH", "提现"),
    ;
    private final String value;

    private final String desc;
}
