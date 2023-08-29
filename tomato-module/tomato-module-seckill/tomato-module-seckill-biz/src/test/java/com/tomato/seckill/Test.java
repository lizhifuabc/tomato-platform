package com.tomato.seckill;

/**
 * Test
 *
 * @author lizhifu
 * @since 2023/3/22
 */
public class Test {

	private static final String script = "for i = 1, #ARGV, 2 do\n"
			+ "  redis.call('zadd', KEYS[1], ARGV[i], ARGV[i+1])\n" + "end\n" + "return true";

	public static void main(String[] args) {
		System.out.println(script);
	}

}
