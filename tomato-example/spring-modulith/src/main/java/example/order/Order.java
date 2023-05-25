package example.order;

import example.order.Order.OrderIdentifier;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

/**
 * 订单聚合根
 */
public class Order implements AggregateRoot<Order, OrderIdentifier> {

	private @Getter OrderIdentifier id = new OrderIdentifier(UUID.randomUUID());

	public static record OrderIdentifier(UUID id) implements Identifier {}
}
