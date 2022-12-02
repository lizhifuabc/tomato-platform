package com.tomato.order.order.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 更新订单状态
 *
 * @author lizhifu
 * @date 2022/12/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusBO {
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 期望订单状态，例如由支付中-->支付成功
     * 那么期望订单状态就是支付中
     */
    private String expectOrderStatus;
    /**
     * 当前版本号
     */
    private Integer currentVersion;
}
