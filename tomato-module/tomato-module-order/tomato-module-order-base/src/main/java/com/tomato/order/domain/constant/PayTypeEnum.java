package com.tomato.order.domain.constant;

import com.tomato.domain.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式枚举
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@AllArgsConstructor
@Getter
public enum PayTypeEnum implements BaseEnum {
    /**
     * 1 微信扫码
     */
    WX_SCAN(1, "微信扫码"),

    /**
     * 2 支付宝扫码
     */
    ALI_SCAN(2, "支付宝扫码"),
    ;

    private final Integer value;

    private final String desc;
}
