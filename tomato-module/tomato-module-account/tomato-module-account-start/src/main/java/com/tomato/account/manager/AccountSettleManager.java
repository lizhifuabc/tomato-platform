package com.tomato.account.manager;

import com.tomato.account.dao.AccountSettleDao;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.vo.enums.SettleTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
@Slf4j
public class AccountSettleManager {

	private final AccountSettleDao accountSettleDao;

	public AccountSettleManager(AccountSettleDao accountSettleDao) {
		this.accountSettleDao = accountSettleDao;
	}

	/**
	 * 创建账户结算规则
	 * @param accountSettleEntity 账户结算规则
	 */
	public AccountSettleEntity create(AccountSettleEntity accountSettleEntity) {
		// 自动结算（定期结算）
		if (accountSettleEntity.getSettleType().equals(SettleTypeEnum.AUTO_SETTLEMENT.getValue())) {
			// 升序
			String[] cycleData = accountSettleEntity.getCycleData().split(",");
			Arrays.sort(cycleData);
			accountSettleEntity.setCycleData(StringUtils.join(cycleData, ","));
		}
		// 默认最大结算天数 Integer.MAX_VALUE,预留，是否手动结算，选择日期
		if (accountSettleEntity.getMaxSettleDays() == null || accountSettleEntity.getMaxSettleDays() == 0) {
			accountSettleEntity.setMaxSettleDays(Integer.MAX_VALUE);
		}
		// 保存结算规则
		accountSettleEntity.setCreateTime(LocalDateTime.now());
		accountSettleDao.insert(accountSettleEntity);
		log.info("设置结算规则, merchantNo:{}, id:{}", accountSettleEntity.getMerchantNo(), accountSettleEntity.getId());
		return accountSettleEntity;
	}

	/**
	 * 查询
	 * @param accountNo 账号
	 * @return 账户结算规则
	 */
	public Optional<AccountSettleEntity> selectByAccountNo(String accountNo) {
		return Optional.ofNullable(accountSettleDao.selectByAccountNo(accountNo));
	}

	/**
	 * 更新 + 乐观锁
	 * @param accountSettleEntity 账户结算规则
	 */
	public void updateByAccountNo(AccountSettleEntity accountSettleEntity) {
		int count = accountSettleDao.updateByAccountNo(accountSettleEntity);
		if (count == 0) {
			throw new RuntimeException("更新账户结算规则失败");
		}
	}

}
