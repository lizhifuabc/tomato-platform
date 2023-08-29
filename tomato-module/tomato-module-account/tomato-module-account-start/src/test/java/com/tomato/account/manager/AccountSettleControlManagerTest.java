package com.tomato.account.manager;

import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.vo.enums.CycleTypeEnum;
import com.tomato.account.vo.enums.SettleTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * AccountSettleControlManager
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@SpringBootTest
public class AccountSettleControlManagerTest {

	@Resource
	AccountSettleControlManager accountSettleControlManager;

	@Test
	public void test() {
		AccountSettleEntity accountSettleEntity = new AccountSettleEntity();
		accountSettleEntity.setId(1000L);
		accountSettleEntity.setAccountNo("12312");
		accountSettleEntity.setMerchantNo("12312");
		accountSettleEntity.setCreateTime(LocalDateTime.now());
		accountSettleEntity.setSettleType(SettleTypeEnum.AUTO_SETTLEMENT.getValue());
		accountSettleEntity.setCycleData("1,2,4");
		accountSettleEntity.setCycleType(CycleTypeEnum.WEEK.getValue());
		accountSettleEntity.setReserveDays(3);
		accountSettleControlManager.create(accountSettleEntity);
	}

}
