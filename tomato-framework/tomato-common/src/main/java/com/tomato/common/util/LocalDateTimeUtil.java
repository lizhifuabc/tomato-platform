package com.tomato.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * {@link LocalDateTime} 工具类封装
 *
 * @author lizhifu
 * @since 2023/8/8
 */
public class LocalDateTimeUtil {

	/**
	 * {@link LocalDateTime} 转换为时间戳
	 * @param millis 时间戳
	 * @return 时间戳
	 */
	public static LocalDateTime convertMillisToLocalDateTime(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
	}

	/**
	 * localDateTime 转换为 String {@link LocalDateTime} 转换为时间戳
	 * @param localDateTime {@link LocalDateTime} 对象
	 * @return 时间戳
	 */
	public static String convertLocalDateTimeToString(LocalDateTime localDateTime, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(formatter);
	}

}
