package com.tomato.account.vo.req;

import com.tomato.account.vo.enums.AccountHisTypeEnum;
import com.tomato.account.vo.enums.AccountTypeEnum;
import com.tomato.validator.annotation.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 账户系统收单请求
 *
 * @author lizhifu
 * @since  2022/6/7
 */
@Data
@Builder
@Tag(name = "账户系统收单请求")
@NoArgsConstructor
@AllArgsConstructor
public class AccountTradReq {
    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    @Schema(description = "商户编号")
    private String merchantNo;
    /**
     * 发生金额
     */
    @Digits(integer = 16, fraction=2, message = "amount格式不正确")
    @NotNull(message = "amount不为空")
    @Schema(description = "发生金额")
    private BigDecimal amount;
    /**
     * 第三方流水号
     */
    @NotBlank(message = "系统流水号不能为空")
    @Schema(description = "系统流水号")
    private String sysNo;
    /**
     * 商户订单号
     */
    @NotBlank(message = "商户订单号不能为空")
    @Schema(description = "商户订单号")
    private String merchantOrderNo;
    /**
     * 账户历史类型
     */
    @NotBlank(message = "账户历史类型不能为空")
    @CheckEnum(value = AccountHisTypeEnum.class, message = "账户历史类型不正确")
    @Schema(description = "账户历史类型")
    private String accountHisType;

    /**
     * 账户类型
     */
    @NotBlank(message = "账户类型不能为空")
    @CheckEnum(value = AccountTypeEnum.class, message = "账户类型不正确")
    @Schema(description = "账户类型")
    private String accountType;
}
