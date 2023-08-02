package com.tomato.order.application.event;

import com.tomato.order.domain.repository.RabbitRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 订单创建监听
 *
 * @author lizhifu
 * @since 2023/8/2
 */
@Order(0)
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreateListener implements ApplicationListener<OrderCreateEvent> {
    private final RabbitRepository rabbitRepository;
    @Override
    public void onApplicationEvent(@NotNull OrderCreateEvent event) {
        log.info("OrderCreateListener 订单创建监听:{}",event.getOrderInfoEntity().getOrderNo());
        rabbitRepository.createOrderTimeOut(event.getOrderInfoEntity());
    }
}
