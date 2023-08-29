package com.tomato.utils.base.date;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 日期格式化 copy from hutool
 *
 * @author lizhifu
 * @date 2022/12/9
 */
public class DatePattern {

	/**
	 * 标准日期格式：yyyyMMddHHmmss
	 */
	public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

	/**
	 * 标准日期格式 yyyyMMddHHmmss
	 */
	public static final DateTimeFormatter PURE_DATETIME_FORMATTER = createFormatter(PURE_DATETIME_PATTERN);

	/**
	 * 标准日期格式：yyyyMMdd
	 */
	public static final String PURE_DATE_PATTERN = "yyyyMMdd";

	/**
	 * 标准日期格式
	 */
	public static final DateTimeFormatter PURE_DATE_FORMATTER = createFormatter(PURE_DATE_PATTERN);

	/**
	 * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
	 */
	public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 标准日期时间格式，精确到秒 yyyy-MM-dd HH:mm:ss
	 */
	public static final DateTimeFormatter NORM_DATETIME_FORMATTER = createFormatter(NORM_DATETIME_PATTERN);

	/**
	 * 创建并为 {@link DateTimeFormatter} 赋予默认时区和位置信息，默认值为系统默认值。
	 * @param pattern 日期格式
	 * @return {@link DateTimeFormatter}
	 * @since 5.7.5
	 */
	public static DateTimeFormatter createFormatter(String pattern) {
		return DateTimeFormatter.ofPattern(pattern, Locale.getDefault()).withZone(ZoneId.systemDefault());
	}

}
