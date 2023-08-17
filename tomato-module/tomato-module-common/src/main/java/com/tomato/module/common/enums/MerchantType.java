package com.tomato.module.common.enums;

import com.tomato.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商户类型
 *
 * @author lizhifu
 * @since 2023/8/17
 */
@AllArgsConstructor
@Getter
public enum MerchantType implements BaseEnum<String> {
    /**
     * 支付商户
     */
    PAY("PAY", "支付商户"),

    /**
     * 代理商商户
     */
    AGENT("AGENT", "代理商商户"),
    ;

    private final String value;

    private final String desc;
}
