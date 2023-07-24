package com.tomato.merchant.domain.req;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户费率
 *
 * @author lizhifu
 * @since 2023/7/23
 */
@Data
public class MerchantRateReq {
    /**
     * 费率
     */
    @Digits(integer = 9, fraction=2, message = "费率格式不正确")
    @DecimalMin(value = "0.00", message = "费率格式不正确")
    @NotNull(message = "费率不为空")
    private BigDecimal rate;
    /**
     * 支付方式
     */
    @NotNull(message = "支付方式不能为空")
    private Integer payType;
}
