package com.tomato.utils.base.lang;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * uuid 生成
 *
 * @author lizhifu
 * @since 2022/12/15
 */
public class UUIDGenerator {

	private final static String DASH = "-";

	private final static String EMPTY = "";

	/**
	 * ThreadLocalRandom 获取 32 位 UUID 去掉"-"
	 */
	public static String get32UUID() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		return new UUID(random.nextLong(), random.nextLong()).toString().replace(DASH, EMPTY);
	}

	public static void main(String[] args) {
		System.out.println(get32UUID());
	}

}
