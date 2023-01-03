package com.tomato.order.order.manager;

import com.tomato.order.properties.InitProperties;
import com.tomato.order.properties.SnowProperties;
import com.tomato.order.util.OrderNoGenUtil;
import org.springframework.stereotype.Service;

/**
 * 订单编号
 *
 * @author lizhifu
 * @since 2023/1/3
 */
@Service
public class OrderNoManager {
    private final OrderNoGenUtil orderNoGenUtil;
    private final SnowProperties snowProperties;
    private final InitProperties initProperties;
    public OrderNoManager(SnowProperties snowProperties, InitProperties initProperties) {
        this.snowProperties = snowProperties;
        this.initProperties = initProperties;
        this.orderNoGenUtil = new OrderNoGenUtil(
                this.snowProperties.getDatacenterId(),
                this.snowProperties.getWorkerId(),
                this.initProperties.getDbCount(),
                this.initProperties.getTableCount());
    }

    public String genOrderNo(String merchantOrderNo, String merchantNo) {
        return orderNoGenUtil.genOrderNo(merchantOrderNo,merchantNo);
    }
}
