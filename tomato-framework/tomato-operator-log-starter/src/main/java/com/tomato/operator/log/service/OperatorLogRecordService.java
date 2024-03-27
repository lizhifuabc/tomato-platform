package com.tomato.operator.log.service;

import com.tomato.operator.log.domain.LogRecord;

/**
 * 操作日志记录
 *
 * @author lizhifu
 * @since 2024/3/13
 */
public interface OperatorLogRecordService {
	/**
	 * 保存 log
	 *
	 * @param logRecord 日志实体
	 */
	void record(LogRecord logRecord);
}
