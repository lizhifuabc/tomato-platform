package com.tomato.order.application.component;

import com.tomato.order.domain.constants.ShardingConstant;
import org.springframework.stereotype.Component;

/**
 * 订单编号组件
 *
 * @author lizhifu
 * @since 2023/8/4
 */
@Component
public class OrderNoComponent {
    /**
     * 生成订单编号
     * @return 订单编号
     */
    public String createOrderNo(String merchantNo) {
        // 商户号的后六位数字
        return System.currentTimeMillis() + ShardingConstant.merchantNoSpilt(merchantNo);
    }
    public static void main(String[] args) {
        OrderNoComponent orderNoComponent = new OrderNoComponent();
        System.out.println(orderNoComponent.createOrderNo("10202307240001001"));
    }
}
