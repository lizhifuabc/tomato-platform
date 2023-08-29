package com.tomato.common.util;

import java.net.NetworkInterface;
import java.time.LocalDateTime;
import java.util.Enumeration;

/**
 * IdWorker
 *
 * @author lizhifu
 * @since 2023/8/8
 */
public class IdWorkerTest {

	public static void main(String[] args) throws Exception {
		System.out.println("当前时间：" + System.currentTimeMillis());
		IdWorker idWorker = new IdWorker(187L);
		for (int i = 0; i < 100; i++) {
			long nextId = idWorker.nextId();
			System.out.println("生成的ID：" + nextId);
		}
		long nextId = idWorker.nextId();
		System.out.println("生成的ID：" + nextId);

		long workerId = idWorker.getWorkerId(nextId);
		System.out.println("生成的ID转workerId：" + workerId);

		long timeMillis = idWorker.getTimeMillis(nextId);
		System.out.println("生成的ID转时间戳：" + timeMillis);

		LocalDateTime localDateTime = LocalDateTimeUtil.convertMillisToLocalDateTime(timeMillis);
		System.out.println("生成的ID转LocalDateTime：" + localDateTime);

		String localDateTimeToString = LocalDateTimeUtil.convertLocalDateTimeToString(localDateTime,
				"yyyy-MM-dd HH:mm:ss");
		System.out.println("生成的ID转localDateTimeToString：" + localDateTimeToString);
	}

	public static long generateWorkerIdBaseOnMac() throws Exception {
		Enumeration<NetworkInterface> all = NetworkInterface.getNetworkInterfaces();
		while (all.hasMoreElements()) {
			NetworkInterface networkInterface = all.nextElement();
			boolean isLoopback = networkInterface.isLoopback();
			boolean isVirtual = networkInterface.isVirtual();
			if (isLoopback || isVirtual) {
				continue;
			}
			byte[] mac = networkInterface.getHardwareAddress();
			return ((mac[4] & 0B11) << 8) | (mac[5] & 0xFF);
		}
		throw new RuntimeException("找不到可用的mac地址");
	}

}
