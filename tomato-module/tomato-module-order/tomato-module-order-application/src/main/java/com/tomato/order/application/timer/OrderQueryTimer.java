package com.tomato.order.application.timer;

import com.tomato.order.domain.constants.OrderStatusEnum;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import com.tomato.order.domain.repository.OrderInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 补单定时任务
 * TODO 定时锁
 * TODO 下游订单查询
 * TODO 异步执行，下游查单 && 数据查询list
 * @author lizhifu
 * @since 2022/12/28
 */
@Slf4j
@Component
public class OrderQueryTimer {
    private final OrderInfoRepository orderInfoRepository;

    /**
     * 查询数量
     */
    private static final int PAGE_SIZE = 100;

    public OrderQueryTimer(OrderInfoRepository orderInfoRepository) {
        this.orderInfoRepository = orderInfoRepository;
    }

    /**
     * 每分钟执行一次
     */
    @Scheduled(cron="0 0/1 * * * ?")
    public void start() {
        log.info("补单定时任务 start");
        // 当前时间 - 10分钟。
        LocalDateTime start = LocalDateTime.now().minusMinutes(10);
        // 当前页码
        int pageIndex = 0;
        while(true){
            try {
                // 支付中的订单 && （ 订单创建时间 + 10分钟 >= 当前时间 ）TODO 异步执行
                List<OrderInfoEntity> orderList = orderInfoRepository.selectByCreateTime(pageIndex, PAGE_SIZE, start, OrderStatusEnum.DEAL.getValue());
                // 查询无结果
                if (orderList == null || orderList.isEmpty()){
                    break;
                }
                orderList.forEach(orderEntity -> {
                    // TODO 异步执行
                    log.info("补单定时任务 orderNo:{}",orderEntity.getOrderNo());
                });
                pageIndex++;
            } catch (Exception e){
                // 出现异常，直接退出，避免死循环
                log.error("补单定时任务 error", e);
                break;
            }
        }
    }
}
