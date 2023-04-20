package com.tomato.account.domain.req;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户提现
 *
 * @author lizhifu
 * @since  2022/6/7
 */
@Data
@Tag(name = "账户提现")
public class AccountCashReq {
    /**
     * 账户编号
     */
    @NotBlank(message = "账户编号不能为空")
    @Schema(description = "账户编号")
    private String accountNo;
    /**
     * 发生金额
     */
    @Digits(integer = 16, fraction=2, message = "amount格式不正确")
    @DecimalMin(value = "0.01", message = "amount最小值为0.01")
    @NotNull(message = "amount不为空")
    @Schema(description = "发生金额")
    private BigDecimal amount;

    /**
     * 提现银行卡ID
     */
    @Schema(description = "提现银行卡ID")
    @NotNull(message = "提现银行卡ID不能为空")
    private Long bankCardId;
}
