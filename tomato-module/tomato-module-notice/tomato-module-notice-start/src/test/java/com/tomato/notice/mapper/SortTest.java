package com.tomato.notice.mapper;

import com.tomato.mybatis.domain.Sort;

/**
 * Sort
 *
 * @author lizhifu
 * @since 2023/5/23
 */
public class SortTest {

	public static void main(String[] args) {
		Sort sort = Sort.by("id").and("name");
		sort.getOrders().stream().forEach(order -> {
			System.out.println(order.column());
			System.out.println(order.direction());
		});
	}

}
