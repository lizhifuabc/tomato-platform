package com.tomato.book.redis.support.incrementer;

/**
 * 唯一id生成器
 *
 * @author lizhifu
 * @since 2023/2/27
 */
public class MaxValueIncrementer {

	private long nextId = 0L;

	private long maxId = 0L;

	public synchronized long getNextKey() {
		if (this.maxId == this.nextId) {

		}
		else {
			++this.nextId;
		}
		return this.nextId;
	}

}
