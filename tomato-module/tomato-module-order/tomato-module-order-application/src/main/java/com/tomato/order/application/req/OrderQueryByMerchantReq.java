package com.tomato.order.application.req;

import com.tomato.order.application.req.base.BaseReq;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单查询
 *
 * @author lizhifu
 * @since 2023/4/7
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderQueryByMerchantReq extends BaseReq {
    /**
     * 交易商户订单号
     */
    @NotBlank(message="商户订单号不能为为空！")
    private String merchantOrderNo;
}
