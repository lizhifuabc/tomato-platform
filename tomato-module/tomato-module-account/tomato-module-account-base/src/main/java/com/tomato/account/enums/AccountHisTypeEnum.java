package com.tomato.account.enums;

import com.tomato.domain.type.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户历史类型
 *
 * @author lizhifu
 * @date 2022/7/14
 */
@AllArgsConstructor
@Getter
public enum AccountHisTypeEnum implements BaseEnum {
    /**
     * 结算
     */
    SETTLEMENT("SETTLEMENT", "结算"),
    /**
     * 交易
     */
    TRAD("TRAD", "交易"),
    /**
     * 转账
     */
    TRANS("TRANS", "转账"),
    /**
     * 退款
     */
    REFUND("REFUND", "退款"),
    /**
     * 提现
     */
    CASH("CASH", "提现"),
    ;
    private final String value;

    private final String desc;
}
