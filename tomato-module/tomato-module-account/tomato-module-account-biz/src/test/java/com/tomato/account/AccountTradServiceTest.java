package com.tomato.account;

import com.tomato.account.domain.req.AccountTradReq;
import com.tomato.account.service.AccountTradService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * AccountTradService
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@SpringBootTest
public class AccountTradServiceTest {
    @Resource
    AccountTradService accountTradService;

    @Test
    public void test(){
        AccountTradReq accountTradReq = new AccountTradReq();
        accountTradReq.setAccountNo("102023010703528952850");
        accountTradReq.setAccountType("SETTLEMENT");
        accountTradReq.setAccountHisType("SETTLEMENT");
        accountTradReq.setAmount(new BigDecimal(100));
        accountTradReq.setThirdNo(UUID.randomUUID().toString());
        accountTradReq.setMerchantNo("1673103402850");
        accountTradService.add(accountTradReq);

        accountTradReq.setThirdNo(UUID.randomUUID().toString());
        accountTradService.addAsync(accountTradReq);

        accountTradReq.setThirdNo(UUID.randomUUID().toString());
        accountTradReq.setAmount(new BigDecimal(-600));
        accountTradService.deduct(accountTradReq);
    }
}
