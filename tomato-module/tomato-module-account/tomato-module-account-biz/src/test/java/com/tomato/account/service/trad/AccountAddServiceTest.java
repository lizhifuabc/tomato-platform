package com.tomato.account.service.trad;

import com.tomato.account.domain.req.AccountTradReq;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * AccountAddService
 *
 * @author lizhifu
 * @since 2023/4/18
 */
@SpringBootTest
public class AccountAddServiceTest {
    @Resource
    AccountAddService accountAddService;

    @Test
    public void test(){
        AccountTradReq accountTradReq = new AccountTradReq();
        accountTradReq.setAccountNo("123");
        accountTradReq.setAmount(BigDecimal.ONE);
        accountAddService.exe(accountTradReq);
    }
}
