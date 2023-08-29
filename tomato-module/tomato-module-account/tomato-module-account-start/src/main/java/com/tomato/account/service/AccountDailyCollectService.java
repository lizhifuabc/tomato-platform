package com.tomato.account.service;

import com.tomato.account.dao.AccountDailyCollectDao;
import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.entity.AccountDailyCollectEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 每日待结算汇总
 *
 * @author lizhifu
 * @since 2023/8/23
 */
@Service
@RequiredArgsConstructor
public class AccountDailyCollectService {

	private final AccountDailyCollectDao accountDailyCollectDao;

	private final AccountHisDao accountHisDao;

	public void exe(String accountNo, LocalDate collectDate) {
		LocalDateTime startDate = collectDate.atTime(LocalTime.MIN);
		LocalDateTime endDate = collectDate.atTime(LocalTime.MAX);
		List<AccountDailyCollectEntity> accountDailyCollectEntities = accountHisDao.collectGroup(accountNo, startDate,
				endDate);
		accountDailyCollectEntities.forEach(accountDailyCollectEntity -> {
			accountDailyCollectEntity.setCollectDate(collectDate);
		});
		// TODO 事务拆分
		AccountDailyCollectEntity delete = new AccountDailyCollectEntity();
		delete.setAccountNo(accountNo);
		delete.setCollectDate(collectDate);
		accountDailyCollectDao.deleteByCriteria(delete);
		accountDailyCollectDao.batchInsertSelective(accountDailyCollectEntities);
	}

}
