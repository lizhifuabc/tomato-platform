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
public class OrderQueryOrderNoReq {
    /**
     * 订单号
     */
    @NotBlank
    private String orderNo;
    @NotBlank
    private String hmac;
}
