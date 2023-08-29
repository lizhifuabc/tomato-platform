package com.tomato.redis;

/**
 * test
 *
 * @author lizhifu
 * @since 2023/8/29
 */
public class Test {
	public static void main(String[] args) {
		int bucket = 20;
		String key = "test";
		String hashKey = "test";
		String t = String.format(key, Math.abs(hashKey.hashCode() % bucket));
		System.out.println(t);
	}
}
