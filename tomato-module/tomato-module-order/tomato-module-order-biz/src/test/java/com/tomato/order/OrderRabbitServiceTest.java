package com.tomato.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tomato.order.order.domain.bo.OrderDelayBO;
import com.tomato.order.order.service.OrderRabbitService;
import com.tomato.util.date.DatePattern;
import com.tomato.util.thread.ThreadUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * OrderRabbitService
 *
 * @author lizhifu
 * @since 2022/12/20
 */
@SpringBootTest
public class OrderRabbitServiceTest {
    @Resource
    OrderRabbitService orderRabbitService;

    @Test
    public void test() throws JsonProcessingException {
        OrderDelayBO orderDelayBO = new OrderDelayBO();
        orderDelayBO.setOrderNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        orderRabbitService.delayOrder(orderDelayBO);
        ThreadUtil.sleep(1000 * 60);
    }
}
