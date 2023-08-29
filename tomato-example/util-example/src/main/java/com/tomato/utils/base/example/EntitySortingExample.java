package com.tomato.utils.base.example;

import com.google.common.collect.ImmutableSortedMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntitySortingExample {

	public static void main(String[] args) {
		// 创建一个实体类对象
		MyEntity myEntity = new MyEntity("22222", 1, "3", "hmac");
		// 获取实体类的字段数组
		Field[] fields = MyEntity.class.getDeclaredFields();
		// 使用 ImmutableSortedMap 按字段名字母顺序排序字段
		ImmutableSortedMap<String, Field> sortedFields = ImmutableSortedMap.<String, Field>naturalOrder()
			.putAll(Arrays.stream(fields)
				.filter(field -> !Objects.equals(field.getName(), "hmac"))
				.collect(Collectors.toMap(Field::getName, field -> field)))
			.build();
		// 拼接排序后的字段值
		String result = sortedFields.values().stream().peek(field -> field.setAccessible(true)).map(field -> {
			try {
				return field.get(myEntity);
			}
			catch (IllegalAccessException ignored) {
			}
			return "";
		}).filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining("&"));

		System.out.println("结果: " + result);
	}

	// 示例实体类
	static class MyEntity {

		private String a;

		private int b;

		private String c;

		private String hmac;

		public MyEntity(String a, int b, String c, String hmac) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.hmac = hmac;
		}

	}

}
