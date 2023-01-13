package com.tomato.account.domain.bo;

import com.tomato.account.enums.AccountHisTypeEnum;
import com.tomato.validator.annotation.CheckEnum;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户历史
 *
 * @author lizhifu
 * @since 2023/1/13
 */
@Data
public class AccountHisBO {
    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    private String accountNo;
    /**
     * 发生金额
     */
    @Digits(integer = 16, fraction=2, message = "amount格式不正确")
    @NotNull(message = "amount不为空")
    private BigDecimal amount;
    /**
     * 手续费
     */
    private BigDecimal amountFree;
    /**
     * 第三方流水号
     */
    @NotBlank(message = "第三方流水号不能为空")
    private String thirdNo;
    /**
     * 商户订单号
     */
    private String merchantOrderNo;
    /**
     * 发生类型
     */
    @NotBlank(message = "账户历史类型")
    @CheckEnum(value = AccountHisTypeEnum.class, message = "账户历史类型错误")
    private String accountHisType;
}
