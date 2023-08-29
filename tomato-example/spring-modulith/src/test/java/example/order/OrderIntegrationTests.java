package example.order;

import example.order.Order;
import example.order.OrderCompleted;
import example.order.OrderManagement;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

/**
 * @author Oliver Drotbohm
 */
@ApplicationModuleTest
@RequiredArgsConstructor
class OrderIntegrationTests {

	private final OrderManagement orders;

	@Test
	void publishesOrderCompletion(Scenario scenario) {

		var reference = new Order();

		scenario.stimulate(() -> orders.complete(reference))
			.andWaitForEventOfType(OrderCompleted.class)
			.matchingMappedValue(OrderCompleted::orderId, reference.getId())
			.toArrive();
	}

}
