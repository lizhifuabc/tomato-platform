package com.tomato.account.service;

import com.tomato.account.domain.req.AccountBankCardCreateReq;
import com.tomato.account.domain.req.AccountSettleCreateReq;
import jakarta.annotation.Resource;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountSettleService
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@SpringBootTest
public class AccountSettleServiceTest {
    @Resource
    AccountSettleManagerService accountSettleManagerService;

    public void test(){
        AccountSettleCreateReq accountSettleCreateReq = new AccountSettleCreateReq();
        AccountBankCardCreateReq accountBankCardCreateReq = new AccountBankCardCreateReq();
        accountSettleManagerService.create(accountSettleCreateReq,accountBankCardCreateReq);
    }
}
