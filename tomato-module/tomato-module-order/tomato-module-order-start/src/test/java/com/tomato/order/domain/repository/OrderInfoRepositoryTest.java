package com.tomato.order.domain.repository;

import com.tomato.order.domain.constants.OrderStatusEnum;
import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderInfoRepository
 *
 * @author lizhifu
 * @since 2023/8/2
 */
@SpringBootTest
public class OrderInfoRepositoryTest {
    @Resource
    OrderInfoRepository orderInfoRepository;

    @Test
    public void test() {
        List<OrderInfoEntity> orderList = orderInfoRepository.selectByCreateTime
                (0, 100, LocalDateTime.now().minusDays(1), OrderStatusEnum.TIMEOUT.getValue());
        System.out.println(orderList);
    }
}
