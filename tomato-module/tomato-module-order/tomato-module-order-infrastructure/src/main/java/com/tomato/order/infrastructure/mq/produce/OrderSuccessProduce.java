package com.tomato.order.infrastructure.mq.produce;

import com.tomato.order.domain.repository.RabbitRepository;
import org.springframework.stereotype.Component;

/**
 * 订单支付成功
 *
 * @author lizhifu
 * @since 2023/8/9
 */
@Component
public class OrderSuccessProduce {

	private final RabbitRepository rabbitRepository;

	public OrderSuccessProduce(RabbitRepository rabbitRepository) {
		this.rabbitRepository = rabbitRepository;
	}

}
