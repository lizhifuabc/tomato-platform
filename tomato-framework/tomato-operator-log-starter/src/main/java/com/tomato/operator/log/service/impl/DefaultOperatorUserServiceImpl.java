package com.tomato.operator.log.service.impl;

import com.tomato.operator.log.domain.Operator;
import com.tomato.operator.log.service.OperatorUserService;

/**
 * 默认操作人
 *
 * @author lizhifu
 * @since 2024/3/13
 */
public class DefaultOperatorUserServiceImpl implements OperatorUserService {
	@Override
	public Operator getUser() {
		Operator operator = new Operator();
		operator.setUserName("系统记录");
		return operator;
	}
}
