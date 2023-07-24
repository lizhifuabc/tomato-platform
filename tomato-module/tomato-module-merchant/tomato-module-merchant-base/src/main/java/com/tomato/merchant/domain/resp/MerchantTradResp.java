package com.tomato.merchant.domain.resp;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户交易请求返回
 *
 * @author lizhifu
 * @since 2023/7/24
 */
@Data
@Builder
public class MerchantTradResp {
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
}
