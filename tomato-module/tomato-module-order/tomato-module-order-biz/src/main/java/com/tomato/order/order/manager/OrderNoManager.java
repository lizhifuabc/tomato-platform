package com.tomato.order.order.manager;

import org.springframework.stereotype.Service;

/**
 * 订单编号生成策略
 * 订单号 = 时间戳|分库编号|分表编号|随机数
 * 时间戳+用户标识码+随机数
 * @author lizhifu
 * @date 2022/12/1
 */
@Service
public class OrderNoManager {
    public String genOrderNo(){
        // TODO
        long currentTimeMillis = System.currentTimeMillis();
        return String.valueOf(currentTimeMillis);
    }
}
