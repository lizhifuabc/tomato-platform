package com.tomato.order.application.timer;

import com.tomato.order.domain.repository.OrderInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单过期定时任务
 * TODO 定时锁
 * TODO 订单超时了，此时回调处理
 * TODO 是否需要去下游渠道做一次订单查询（sql 的形式无法处理，需要取舍）
 * @author lizhifu
 * @since 2022/12/28
 */
@Slf4j
@Component
public class OrderTimeOutTimer {
    private final OrderInfoRepository orderInfoRepository;

    public OrderTimeOutTimer(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    /**
     * 每分钟执行一次
     */
    @Scheduled(cron="0 0/1 * * * ?")
    public void start() {
        int updateCount = orderInfoRepository.updateTimeOutOrder();
        log.info("处理订单超时{}条", updateCount);
    }
}
