package example;

import example.order.Order;
import example.order.OrderManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动
 *
 */
@EnableAsync
@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {

	public static void main(String... args) throws Exception {

		SpringApplication.run(Application.class, args)
				.getBean(OrderManagement.class)
				.complete(new Order());
	}
}
