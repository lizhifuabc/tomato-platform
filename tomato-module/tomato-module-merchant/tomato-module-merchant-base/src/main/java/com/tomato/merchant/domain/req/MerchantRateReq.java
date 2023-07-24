package com.tomato.merchant.domain.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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


    /**
     * 商户编号
     */
    @NotBlank(message = "商户编号不能为空")
    private String merchantNo;

    /**
     * 交易费率
     */
    @Digits(integer = 9, fraction=2, message = "交易费率格式不正确")
    @DecimalMin(value = "0.00", message = "交易费率格式不正确")
    @NotNull(message = "交易费率不为空")
    private BigDecimal tradeRate;

    /**
     * 分账费率
     */
    @Digits(integer = 9, fraction=2, message = "分账费率格式不正确")
    @DecimalMin(value = "0.00", message = "分账费率格式不正确")
    private BigDecimal splitRate;

    /**
     * 将来交易费率
     */
    @Digits(integer = 9, fraction=2, message = "将来交易费率格式不正确")
    @DecimalMin(value = "0.00", message = "将来交易费率格式不正确")
    private BigDecimal futureTradeRate;

    /**
     * 将来分账费率
     */
    @Digits(integer = 9, fraction=2, message = "将来分账费率格式不正确")
    @DecimalMin(value = "0.00", message = "将来分账费率格式不正确")
    private BigDecimal futureSplitRate;
    /**
     * 将来分账费率生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime splitRateEffectiveTime;
    /**
     * 将来交易费率生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tradeRateEffectiveTime;
    /**
     * 交易是否停用: 0-否, 1-是
     */
    private Integer tradeStatus = 0;
    /**
     * 分账是否停用: 0-否, 1-是
     */
    private Integer splitStatus = 0;
}
