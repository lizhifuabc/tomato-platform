package com.tomato.book.java;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap
 *
 * @author lizhifu
 * @since 2023/9/5
 */
public class ConcurrentHashMapTest {
	public static void main(String[] args) {
		HashMap<String,String> hashMap = new HashMap<>(16);
		String put = hashMap.put(null, "11");
		System.out.println(put);
	}
}
