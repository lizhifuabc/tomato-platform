package com.tomato.account.service;

import com.tomato.account.enums.CycleTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * AccountWorkService
 *
 * @author lizhifu
 * @since 2023/1/10
 */
@SpringBootTest
public class AccountWorkServiceTest {
    @Resource
    AccountWorkService accountWorkService;

    @Test
    public void test(){
        System.out.println(accountWorkService.nextWorkDay(LocalDate.now(), CycleTypeEnum.MONTH_WORK));
    }
}
