package com.tomato.account.service;

import com.tomato.account.enums.AccountStatusTypeEnum;
import com.tomato.account.enums.AccountTypeEnum;
import com.tomato.account.domain.req.AccountCancelledReq;
import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.account.domain.req.AccountFreezeReq;
import com.tomato.account.service.AccountOperateService;
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
    public void test(){
        AccountCreateReq accountCreateReq = new AccountCreateReq();
        accountCreateReq.setAccountType(AccountTypeEnum.SETTLEMENT.getValue());
        accountCreateReq.setMerchantNo(String.valueOf(System.currentTimeMillis()));
        accountOperateService.createAccount(accountCreateReq);

        AccountFreezeReq accountFreezeReq = new AccountFreezeReq();
        accountOperateService.freezeOrUnfreeze(accountFreezeReq,AccountStatusTypeEnum.ACCOUNT_FROZEN.getValue());

        AccountCancelledReq accountCancelledReq = new AccountCancelledReq();
        accountOperateService.cancelledAccount(accountCancelledReq);
    }
}
