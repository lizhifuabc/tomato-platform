package com.tomato.common.enums;

import com.tomato.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付机构
 *
 * @author lizhifu
 * @since 2023/7/23
 */
@AllArgsConstructor
@Getter
public enum PayInstitutionEnum implements BaseEnum<String> {
    /**
     * 银联
     */
    UNION_PAY("UNION_PAY", "银联"),
    /**
     * 支付宝
     */
    ALI_PAY("ALI_PAY","支付宝"),
    /**
     * 财付通
     */
    TENCENT_PAY("TENCENT_PAY","财付通"),
    /**
     * 网联
     */
    NETS_UNION("NETS_UNION","网联"),
    ;

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    private final String value;

    private final String desc;
}
