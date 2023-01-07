package com.tomato.account;

import com.tomato.account.constant.AccountStatusEnum;
import com.tomato.account.constant.AccountTypeEnum;
import com.tomato.account.domain.req.AccountCancelledReq;
import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.account.domain.req.AccountFreezeReq;
import com.tomato.account.service.AccountManageService;
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
public class AccountManageServiceTest {
    @Resource
    AccountManageService accountManageService;

    @Test
    public void test(){
        AccountCreateReq accountCreateReq = new AccountCreateReq();
        accountCreateReq.setAccountType(AccountTypeEnum.SETTLEMENT.getValue());
        accountCreateReq.setMerchantNo(String.valueOf(System.currentTimeMillis()));
        accountManageService.createAccount(accountCreateReq);

        AccountFreezeReq accountFreezeReq = new AccountFreezeReq();
        accountFreezeReq.setAccountType(accountCreateReq.getAccountType());
        accountFreezeReq.setMerchantNo(accountCreateReq.getMerchantNo());
        accountFreezeReq.setAccountStatus(AccountStatusEnum.ACCOUNT_FROZEN.getValue());
        accountManageService.freezeOrUnfreeze(accountFreezeReq);

        AccountCancelledReq accountCancelledReq = new AccountCancelledReq();
        accountCancelledReq.setAccountType(accountCreateReq.getAccountType());
        accountCancelledReq.setMerchantNo(accountCreateReq.getMerchantNo());
        accountManageService.cancelledAccount(accountCancelledReq);
    }
}
