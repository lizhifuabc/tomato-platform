package com.tomato.reconciliation.task;

import com.tomato.reconciliation.constant.SqlConstant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * SQL解析
 *
 * @author lizhifu
 * @since 2023/5/28
 */
public class TaskSqlAnalysis {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * 解析SQL
	 * @param sql sql
	 * @return sql
	 */
	public static String analysis(String sql, LocalDate taskDate) {
		return sql
			.replaceAll(SqlConstant.MIN_CURRENT_TIME,
					LocalDateTime.of(taskDate, LocalTime.MIN).format(DATE_TIME_FORMATTER))
			.replaceAll(SqlConstant.MAX_CURRENT_TIME,
					LocalDateTime.of(taskDate, LocalTime.MAX).format(DATE_TIME_FORMATTER));
	}

}
