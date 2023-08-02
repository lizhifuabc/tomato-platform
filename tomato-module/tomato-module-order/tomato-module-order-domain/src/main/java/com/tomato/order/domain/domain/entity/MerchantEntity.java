package com.tomato.order.domain.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商户信息
 *
 * @author lizhifu
 * @since 2023/8/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantEntity {
    /**
     * 商户号
     */
    private String merchantNo;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户密钥
     */
    private String merchantKey;
    /**
     * 交易费率
     */
    private BigDecimal trxRate;
    /**
     * 分账费率
     */
    private BigDecimal splitRate;
    /**
     * 支付方式
     */
    private Integer payType;
}
