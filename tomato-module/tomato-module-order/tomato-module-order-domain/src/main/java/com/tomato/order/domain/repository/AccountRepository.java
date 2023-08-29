package com.tomato.order.domain.repository;

import com.tomato.order.domain.domain.entity.AccountEntity;

/**
 * 账户仓储
 *
 * @author lizhifu
 * @since 2023/8/10
 */
public interface AccountRepository {

	/**
	 * 账户入账
	 * @param accountEntity 入账请求
	 */
	void trad(AccountEntity accountEntity);

}
