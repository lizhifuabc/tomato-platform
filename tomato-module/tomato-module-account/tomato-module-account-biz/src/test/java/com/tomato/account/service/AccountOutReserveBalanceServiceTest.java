package com.tomato.account.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountOutReserveBalanceService
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@SpringBootTest
public class AccountOutReserveBalanceServiceTest {
    @Resource
    AccountOutReserveBalanceService accountOutReserveBalanceService;

    @Test
    public void test(){
        accountOutReserveBalanceService.exe("102023011064752054121");
    }
}
