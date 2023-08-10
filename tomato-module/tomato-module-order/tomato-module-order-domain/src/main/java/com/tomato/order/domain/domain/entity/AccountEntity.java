package com.tomato.order.domain.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 账户
 *
 * @author lizhifu
 * @since 2023/8/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    @Schema(description = "商户编号")
    private String merchantNo;
    /**
     * 发生金额
     */
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
}
