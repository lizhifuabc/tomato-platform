package com.tomato.account;

import com.tomato.account.constant.AccountTypeEnum;
import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.account.manager.AccountInfoManager;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountManager
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@SpringBootTest
public class AccountInfoManagerTest {
    @Resource
    AccountInfoManager accountInfoManager;

    @Test
    public void test(){
        AccountCreateReq accountCreateReq = new AccountCreateReq();
        accountCreateReq.setAccountType(AccountTypeEnum.SETTLEMENT.getValue());
        accountCreateReq.setMerchantNo("10202301010004121");
        accountInfoManager.create(accountCreateReq);
    }
}
