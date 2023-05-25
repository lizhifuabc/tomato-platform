package example.order;

import example.order.internal.OrderInternal;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单管理
 * @author lizhifu
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderManagement {

	private final @NonNull ApplicationEventPublisher events;
	private final @NonNull OrderInternal dependency;

	@Transactional
	public void complete(Order order) {
		// 发送订单完成事件
		log.info("发送订单完成事件 {}.", order.getId());
		events.publishEvent(new OrderCompleted(order.getId()));
	}
}
