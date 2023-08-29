package com.tomato.book.java;

/**
 * @author lizhifu {@link VolatileMain}
 * @author lizhifu
 * @since 2023/3/7
 */
public class VolatileDemo {

	public static void main(String[] args) {
		VolatileMain volatileMain = new VolatileMain();
		for (int i = 0; i < 50; i++) {
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					volatileMain.add();
				}
			}).start();
		}
		// 等待上面的线程执行完
		while (Thread.activeCount() > 2) {
			// main线程让出cpu
			Thread.yield();
		}
		System.out.println(volatileMain.i);
	}

}
