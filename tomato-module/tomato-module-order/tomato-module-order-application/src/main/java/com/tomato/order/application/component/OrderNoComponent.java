package com.tomato.order.application.component;

import com.tomato.common.util.IdWorker;
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
    private final IdWorker idWorker;

    public OrderNoComponent(IdWorker idWorker) {
       this.idWorker = idWorker;
    }

    /**
     * 生成订单编号
     * @return 订单编号
     */
    public String createOrderNo(String merchantNo) {
        // 商户号的后六位数字
        return idWorker.nextIdStr() + ShardingConstant.merchantNoSpilt(merchantNo);
    }
}
