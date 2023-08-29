package com.tomato.account.manager;

import com.tomato.account.domain.bo.AccountHisBO;
import com.tomato.account.domain.entity.AccountInfoEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * AccountHisManager
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@SpringBootTest
public class AccountHisManagerTest {

	@Resource
	AccountHisManager accountHisManager;

	@Test
	public void test() {
		AccountInfoEntity accountInfoEntity = new AccountInfoEntity();
		accountInfoEntity.setBalance(new BigDecimal(100));
		accountInfoEntity.setAccountNo(UUID.randomUUID().toString());
		accountInfoEntity.setMerchantNo(UUID.randomUUID().toString());

		AccountHisBO accountHisBO = new AccountHisBO();
		accountHisBO.setAmount(new BigDecimal(100));
		accountHisBO.setSysNo(UUID.randomUUID().toString());
		accountHisManager.insert(accountInfoEntity, accountHisBO);
	}

}
