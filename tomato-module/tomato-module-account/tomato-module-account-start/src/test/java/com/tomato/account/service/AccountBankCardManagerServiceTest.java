package com.tomato.account.service;

import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.vo.req.AccountBankCardCreateReq;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountBankCardManagerService
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@SpringBootTest
public class AccountBankCardManagerServiceTest {

	@Resource
	AccountBankCardManagerService accountBankCardManagerService;

	@Test
	public void test() {
		AccountInfoEntity accountInfo = new AccountInfoEntity();
		accountInfo.setAccountNo("12312");
		accountInfo.setMerchantNo("12312");
		AccountBankCardCreateReq accountBankCardCreateReq = new AccountBankCardCreateReq();
		accountBankCardCreateReq.setBankCode("123123");
		accountBankCardCreateReq.setBankName("fdasf");
		accountBankCardCreateReq.setCardNo("6556556555454544");
		accountBankCardCreateReq.setAccountName("早上");
		accountBankCardCreateReq.setCardType("fadf");
		accountBankCardManagerService.create(accountBankCardCreateReq, accountInfo);
	}

}
