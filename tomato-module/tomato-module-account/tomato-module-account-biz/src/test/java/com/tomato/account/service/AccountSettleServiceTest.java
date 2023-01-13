package com.tomato.account.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * AccountSettleService
 *
 * @author lizhifu
 * @since 2023/1/13
 */
@SpringBootTest
public class AccountSettleServiceTest {
    @Resource
    AccountSettleService accountSettleService;

    @Test
    public void test(){
        LocalDate nextSettleDate = LocalDate.of(2023,1,17);
        String accountNo = "102023011395657079884";
        accountSettleService.settle(nextSettleDate,accountNo);
    }
}
