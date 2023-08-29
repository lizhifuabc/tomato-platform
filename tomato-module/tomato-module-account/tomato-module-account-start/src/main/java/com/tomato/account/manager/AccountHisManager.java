package com.tomato.account.manager;

import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.bo.AccountHisBO;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountRateEntity;
import com.tomato.common.enums.CommonStatusEnum;
import com.tomato.common.exception.BusinessException;
import com.tomato.common.util.BigDecimalUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 账户历史
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@Service
public class AccountHisManager {

	private final AccountHisDao accountHisDao;

	private final AccountRateManager accountRateManager;

	public AccountHisManager(AccountHisDao accountHisDao, AccountRateManager accountRateManager) {
		this.accountHisDao = accountHisDao;
		this.accountRateManager = accountRateManager;
	}

	/**
	 * 插入账户历史:成功
	 * @param account 账户
	 * @param accountHisBO 账户历史
	 * @return 账户历史
	 */
	public AccountHisEntity insert(AccountInfoEntity account, AccountHisBO accountHisBO) {
		AccountHisEntity accountHisEntity = baseAccountHis(account, accountHisBO);
		accountHisEntity.setBeforeBalance(account.getBalance());
		accountHisEntity.setAfterBalance(
				account.getBalance().add(accountHisBO.getAmount()).subtract(accountHisEntity.getAmountFree()));
		accountHisEntity.setAccountHisSerial(account.getAccountHisSerial());
		accountHisEntity.setAccountHisStatus(CommonStatusEnum.SUCCESS.getValue());
		accountHisEntity.setCompleteTime(LocalDateTime.now());
		accountHisDao.insert(accountHisEntity);
		return accountHisEntity;
	}

	/**
	 * 插入账户历史:异步
	 * @param account 账户
	 * @param accountHisBO 账户历史
	 * @return 账户历史
	 */
	public AccountHisEntity insertAsync(AccountInfoEntity account, AccountHisBO accountHisBO) {
		AccountHisEntity accountHisEntity = baseAccountHis(account, accountHisBO);
		accountHisEntity.setAccountHisStatus(CommonStatusEnum.DEAL.getValue());
		accountHisDao.insert(accountHisEntity);
		return accountHisEntity;
	}

	/**
	 * 账户历史基础信息
	 * @param account 账户
	 * @param accountHisBO 账户历史
	 * @return 账户历史
	 */
	private AccountHisEntity baseAccountHis(AccountInfoEntity account, AccountHisBO accountHisBO) {
		AccountHisEntity accountHisEntity = new AccountHisEntity();

		AccountRateEntity rate = accountRateManager.getRate(account.getAccountNo(), accountHisBO.getAccountHisType())
			.orElseThrow(() -> new BusinessException("账户费率不存在"));
		accountHisEntity.setAmountRate(rate.getRate());
		accountHisEntity.setAmountFree(BigDecimalUtil.multiply(accountHisBO.getAmount().abs(), rate.getRate()));

		accountHisEntity.setMerchantOrderNo(accountHisBO.getMerchantOrderNo());
		accountHisEntity.setMerchantNo(account.getMerchantNo());
		accountHisEntity.setAmount(accountHisBO.getAmount());
		accountHisEntity.setAccountHisType(accountHisBO.getAccountHisType());
		accountHisEntity.setAccountNo(account.getAccountNo());
		accountHisEntity.setSysNo(accountHisBO.getSysNo());
		return accountHisEntity;
	}

}
