package com.tomato.account.domain.req;

import com.tomato.account.enums.AccountHisTypeEnum;
import com.tomato.validator.annotation.CheckEnum;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户系统收单请求
 *
 * @author lizhifu
 * @date 2022/6/7
 */
@Data
public class AccountTradReq {
    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    private String accountNo;
    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private String merchantNo;
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
    /**
     * 账户类型
     */
    @NotBlank(message = "账户类型不能为空")
    private String accountType;
}
