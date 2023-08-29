package com.tomato.account.timer;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.service.AccountDailyCollectService;
import com.tomato.account.service.AccountOutReserveBalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 账户定时任务
 *
 * @author lizhifu
 * @since 2023/8/23
 */
@Component
@Slf4j
public class AccountTimer {

	private final AccountInfoDao accountInfoDao;

	private final AccountOutReserveBalanceService accountOutReserveBalanceService;

	private final AccountDailyCollectService accountDailyCollectService;

	/**
	 * 查询数量
	 */
	private static final int PAGE_SIZE = 100;

	public AccountTimer(AccountInfoDao accountInfoDao, AccountOutReserveBalanceService accountOutReserveBalanceService,
			AccountDailyCollectService accountDailyCollectService) {
		this.accountInfoDao = accountInfoDao;
		this.accountOutReserveBalanceService = accountOutReserveBalanceService;
		this.accountDailyCollectService = accountDailyCollectService;
	}

	/**
	 * 每天凌晨1点执行一次
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void run() {
		log.info("账户定时任务执行start:{}", LocalDateTime.now());
		// 当前页码
		int pageIndex = 0;
		while (true) {
			List<String> accountList = accountInfoDao.selectAllAccount(pageIndex, PAGE_SIZE);
			// 查询无结果
			if (accountList == null || accountList.isEmpty()) {
				break;
			}
			for (String accountNo : accountList) {
				log.info("账户定时任务执行:{}", accountNo);
				try {
					log.info("账户定时任务执行:每日待结算汇总定时:{}", accountNo);
					accountDailyCollectService.exe(accountNo, LocalDate.now());
				}
				catch (Exception e) {
					log.error("账户定时任务执行:每日待结算汇总定时:账号:{}出现异常", accountNo, e);
				}
				try {
					log.info("账户定时任务执行:风险预存期外余额定时:{}", accountNo);
					accountOutReserveBalanceService.exe(accountNo);
				}
				catch (Exception e) {
					log.error("账户定时任务执行:风险预存期外余额定时:账号:{}出现异常", accountNo, e);
				}
			}
			pageIndex++;
		}
		log.info("账户定时任务执行end:{}", LocalDateTime.now());
	}

}
