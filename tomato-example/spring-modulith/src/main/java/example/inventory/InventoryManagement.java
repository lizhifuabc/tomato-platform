package example.inventory;

import example.order.OrderCompleted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.stereotype.Service;

/**
 * A Spring {@link Service} exposed by the inventory module.
 *
 * @author Oliver Drotbohm
 */
@Service
@RequiredArgsConstructor
@Slf4j
class InventoryManagement {

	private final InventoryInternal dependency;

	/**
	 * 订单完成事件监听 {@link OrderCompleted} 事件
	 * @param event 订单完成事件
	 * @throws InterruptedException 中断异常
	 */
	@ApplicationModuleListener
	public void on(OrderCompleted event) throws InterruptedException {

		var orderId = event.orderId();

		log.info("接收订单完成事件 {}.", orderId);

		// 模拟库存处理
		Thread.sleep(1000);

		log.info("接收订单完成事件处理完成 {}.", orderId);
	}
}
