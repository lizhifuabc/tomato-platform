package com.tomato.operator.log.service;

import com.tomato.operator.log.domain.Operator;

/**
 * 操作人
 *
 * @author lizhifu
 * @since 2024/3/13
 */
public interface OperatorUserService {
	/**
	 * 获取操作人
	 *
	 * @return 操作人
	 */
	Operator getUser();
}
