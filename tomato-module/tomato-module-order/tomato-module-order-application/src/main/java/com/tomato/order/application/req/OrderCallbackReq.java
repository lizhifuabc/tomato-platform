package com.tomato.order.application.req;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * 订单回调
 *
 * @author lizhifu
 * @since 2023/8/9
 */
@Data
@Tag(name = "订单回调", description = "订单回调")
public class OrderCallbackReq {
    /**
     * 订单号
     */
    private String orderNo;
}
