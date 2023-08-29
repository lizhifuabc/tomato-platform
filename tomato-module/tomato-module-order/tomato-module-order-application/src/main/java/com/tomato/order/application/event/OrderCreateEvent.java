package com.tomato.order.application.event;

import com.tomato.order.domain.domain.entity.OrderInfoEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 订单创建事件
 *
 * @author lizhifu
 * @since 2023/8/2
 */
@Getter
public final class OrderCreateEvent extends ApplicationEvent {

	private final OrderInfoEntity orderInfoEntity;

	public OrderCreateEvent(Object source, OrderInfoEntity orderInfoEntity) {
		super(source);
		this.orderInfoEntity = orderInfoEntity;
	}

}
