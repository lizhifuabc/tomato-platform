package com.tomato.mybatis.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排序
 *
 * @author lizhifu
 * @since 2023/5/23
 */
public class Sort {

	public static final Direction DEFAULT_DIRECTION = Sort.Direction.DESC;

	private final List<Order> orders;

	public Sort and(String column, Direction direction) {
		this.orders.add(new Sort.Order(direction, column));
		return this;
	}

	public Sort and(String column) {
		this.orders.add(new Sort.Order(DEFAULT_DIRECTION, column));
		return this;
	}

	public static Sort by(String column) {
		return by(column, DEFAULT_DIRECTION);
	}

	public static Sort by(String column, Direction direction) {
		return new Sort(direction, Collections.singletonList(column));
	}

	private Sort(Direction direction, List<String> properties) {
		this.orders = properties.stream().map((it) -> new Order(direction, it)).collect(Collectors.toList());
	}

	public List<Order> getOrders() {
		return orders;
	}

	public record Order(Direction direction, String column) implements Serializable {
		@Serial
		private static final long serialVersionUID = 1L;

	}

	/**
	 * 排序方式
	 */
	public enum Direction {

		/**
		 * 升序
		 */
		ASC,
		/**
		 * 降序（默认）
		 */
		DESC;

		Direction() {
		}

	}

}
