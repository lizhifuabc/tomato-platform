package com.tomato.account.constant;

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
     * 支付
     */
    PAYMENT("PAYMENT", "支付");
    private final String value;

    private final String desc;
}
