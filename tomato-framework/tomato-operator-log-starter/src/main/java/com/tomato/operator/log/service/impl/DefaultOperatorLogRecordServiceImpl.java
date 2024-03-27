package com.tomato.operator.log.service.impl;

import com.tomato.operator.log.domain.LogRecord;
import com.tomato.operator.log.service.OperatorLogRecordService;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认操作日志记录
 *
 * @author lizhifu
 * @since 2024/3/13
 */
@Slf4j
public class DefaultOperatorLogRecordServiceImpl implements OperatorLogRecordService {
	@Override
	public void record(LogRecord logRecord) {
		log.info("记录日志:{}", logRecord);
	}
}