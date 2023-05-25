package example.order;

import example.order.Order.OrderIdentifier;
import org.jmolecules.event.types.DomainEvent;

/**
 * 订单完成事件
 */
public record OrderCompleted(OrderIdentifier orderId) implements DomainEvent {}
