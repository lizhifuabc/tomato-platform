package com.tomato.account.service;

import com.tomato.account.vo.enums.AccountStatusTypeEnum;
import com.tomato.account.vo.enums.AccountTypeEnum;
import com.tomato.account.vo.req.AccountCancelledReq;
import com.tomato.account.vo.req.AccountCreateReq;
import com.tomato.account.vo.req.AccountFreezeReq;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountInfoService
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@SpringBootTest
public class AccountOperateServiceTest {

	@Resource
	AccountOperateService accountOperateService;

	@Test
	public void test() {
		AccountCreateReq accountCreateReq = new AccountCreateReq();
		accountCreateReq.setAccountType(AccountTypeEnum.SETTLEMENT.getValue());
		accountCreateReq.setMerchantNo("10202307240001001");
		accountCreateReq.setRemark("测试");
		accountOperateService.createAccount(accountCreateReq);

		AccountFreezeReq accountFreezeReq = new AccountFreezeReq();
		accountOperateService.freezeOrUnfreeze(accountFreezeReq, AccountStatusTypeEnum.ACCOUNT_FROZEN.getValue());

		AccountCancelledReq accountCancelledReq = new AccountCancelledReq();
		accountOperateService.cancelledAccount(accountCancelledReq);
	}

}
