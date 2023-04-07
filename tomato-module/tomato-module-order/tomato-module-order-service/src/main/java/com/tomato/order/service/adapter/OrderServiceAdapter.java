package com.tomato.order.service.adapter;

import com.tomato.order.domain.domain.OrderQueryResultDomain;
import com.tomato.order.infrastructure.entity.OrderInfoEntity;
import org.springframework.stereotype.Service;

/**
 * domain -> entity
 * entity -> domain
 * @author lizhifu
 * @since 2023/4/7
 */
@Service
public class OrderServiceAdapter {
    public OrderQueryResultDomain convert(OrderInfoEntity orderInfoEntity){
        return null;
    }
}
