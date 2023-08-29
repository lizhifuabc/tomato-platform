package com.tomato.account.service;

import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.bo.AccountHisDealBO;
import com.tomato.account.domain.bo.AccountHisDealQueryBO;
import com.tomato.account.domain.bo.AccountHisUpdateBatchBO;
import com.tomato.web.core.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 账户异步入账服务
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
@Slf4j
public class AccountTradAsyncService {

	private final AccountHisDao accountHisDao;

	private final AccountAsyncService accountAsyncService;

	public AccountTradAsyncService(AccountHisDao accountHisDao, AccountAsyncService accountAsyncService) {
		this.accountHisDao = accountHisDao;
		this.accountAsyncService = accountAsyncService;
	}

	@Async("asyncTaskExecutorAccount")
	public void exe(String accountNo) {
		AccountHisDealQueryBO accountHisDealQueryBO = new AccountHisDealQueryBO();
		accountHisDealQueryBO.setAccountNo(accountNo);
		// TODO 增加循环查询
		accountHisDealQueryBO.setLimit(50000);
		AccountHisDealBO accountHisDealBO = accountHisDao.selectDeal(accountHisDealQueryBO);
		if (accountHisDealBO == null) {
			return;
		}
		log.info("账户异步入账服务 accountNo:{},accountHisDealBO:{}", accountNo, accountHisDealBO.getSum());
		AccountHisUpdateBatchBO accountHisUpdateBatchDO = BeanUtil.copy(accountHisDealBO,
				AccountHisUpdateBatchBO.class);
		accountHisUpdateBatchDO.setAccountNo(accountNo);
		accountAsyncService.async(accountHisUpdateBatchDO);
	}

}
