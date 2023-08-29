package example;

import example.order.Order;
import example.order.OrderManagement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test for the overall application.
 *
 * @author Oliver Drotbohm
 */
@SpringBootTest
class ApplicationIntegrationTests {

	@Autowired
	OrderManagement orders;

	@Test
	void completesOrder() throws Exception {

		orders.complete(new Order());

		Thread.sleep(2000);
	}

}
