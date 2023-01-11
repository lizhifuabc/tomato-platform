package com.tomato.account.service;

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
        accountTradReq.setAccountNo("102023011183005784121");
        accountTradReq.setMerchantNo("10202301010004121");
        accountTradReq.setAccountType("SETTLEMENT");
        accountTradReq.setAccountHisType("SETTLEMENT");
        accountTradReq.setAmount(new BigDecimal(100000));
        accountTradReq.setThirdNo(UUID.randomUUID().toString());
        accountTradReq.setMerchantOrderNo(UUID.randomUUID().toString());
        accountTradService.add(accountTradReq);

        accountTradReq.setThirdNo(UUID.randomUUID().toString());
        accountTradService.addAsync(accountTradReq);

        accountTradReq.setThirdNo(UUID.randomUUID().toString());
        accountTradReq.setAmount(new BigDecimal(-600));
        accountTradService.deduct(accountTradReq);

        accountTradReq.setThirdNo(UUID.randomUUID().toString());
        accountTradReq.setAmount(new BigDecimal(-600));
        accountTradService.deductAsync(accountTradReq);
    }
}
