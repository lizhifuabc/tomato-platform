package com.tomato.account.service.trad;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.vo.req.AccountTradReq;
import com.tomato.account.service.AccountCheckService;

import java.math.BigDecimal;

/**
 * 账户交易抽象类
 *
 * @author lizhifu
 * @since 2023/4/17
 */
@Deprecated
public abstract class AbstractAccountTrad {

	private final AccountInfoDao accountInfoDao;

	public AbstractAccountTrad(AccountInfoDao accountInfoDao) {
		this.accountInfoDao = accountInfoDao;
	}

	public void exe(AccountTradReq accountTradReq) {
		AccountInfoEntity account = accountInfoDao.selectByMerchantNo(accountTradReq.getMerchantNo(),
				accountTradReq.getAccountType());
		// 基础校验
		baseCheck(account, accountTradReq);
		// 同步执行
		exe(accountTradReq, account);
	}

	public void exeAsync(AccountTradReq accountTradReq) {
		AccountInfoEntity account = accountInfoDao.selectByMerchantNo(accountTradReq.getMerchantNo(),
				accountTradReq.getAccountType());
		// 基础校验
		baseCheck(account, accountTradReq);
		// 异步执行
		exeAsync(accountTradReq, account);
	}

	/**
	 * 基础校验
	 * @param account 账户信息
	 * @param accountTradReq 入账请求
	 */
	private void baseCheck(AccountInfoEntity account, AccountTradReq accountTradReq) {
		// 1.检查账户是否存在
		AccountCheckService.checkAccountExist(account);
		// 2.校验交易金额
		validateAmount(accountTradReq.getAmount());
	}

	/**
	 * 验证交易金额
	 * @param amount 交易金额
	 */
	private void validateAmount(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) == 0) {
			throw new IllegalArgumentException("交易金额错误");
		}
	}

	/**
	 * 账户入账同步
	 * @param accountTradReq 入账请求
	 */
	protected abstract void exe(AccountTradReq accountTradReq, AccountInfoEntity account);

	/**
	 * 账户入账异步
	 * @param accountTradReq 入账请求
	 */
	protected abstract void exeAsync(AccountTradReq accountTradReq, AccountInfoEntity account);

}
