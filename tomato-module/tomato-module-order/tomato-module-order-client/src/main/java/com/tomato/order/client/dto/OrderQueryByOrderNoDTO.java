package com.tomato.order.client.dto;

import com.tomato.order.client.base.BaseDTO;
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
public class OrderQueryByOrderNoDTO extends BaseDTO {
    /**
     * 订单号
     */
    @NotBlank(message="订单号不能为为空！")
    private String orderNo;
}
