package com.tomato.account.domain.bo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 结算内部账户退款
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@Data
public class AccountRefundBO {
    /**
     * 商户编号
     */
    private String merchantNo;
    /**
     * 原始第三方流水号不能为空
     */
    @NotBlank(message = "原始第三方流水号不能为空")
    private String orgThirdNo;
    /**
     * 新第三方流水号不能为空
     */
    @NotBlank(message = "新第三方流水号不能为空")
    private String thirdNo;
    /**
     * 金额
     */
    @Digits(integer = 16, fraction=2, message = "amount格式不正确")
    @NotNull(message = "amount不为空")
    @DecimalMin("0.00")
    private BigDecimal amount;
}
