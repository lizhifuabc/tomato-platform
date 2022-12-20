package com.tomato.order.domain.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 订单查询
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@Data
public class OrderQueryMerchantReq {
    /**
     * 商户订单号
     */
    @NotBlank
    private String merchantOrderNo;
    /**
     * 商户编号
     */
    @NotBlank
    private String merchantNo;
    @NotBlank
    private String hmac;
}
