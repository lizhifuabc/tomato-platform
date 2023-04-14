package com.tomato.pay.application.req;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 发送第三方通道请求
 *
 * @author lizhifu
 * @date 2022/6/24
 */
@Data
public class ChannelSendReq {
    /**
     * 支付记录号
     */
    @NotBlank(message = "支付记录号不能为空")
    private String payNo;
    /**
     * 请求金额
     */
    @NotNull(message = "请求金额不能为空")
    @DecimalMin(value = "0.01", message = "请求金额不能小于0.01")
    private BigDecimal requestAmount;
    /**
     * 支付方式
     */
    @NotNull(message = "支付方式不能为空")
    private Integer payType;
    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private String merchantNo;
}
