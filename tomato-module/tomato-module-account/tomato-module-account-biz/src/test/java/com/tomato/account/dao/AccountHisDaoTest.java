package com.tomato.account.dao;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AccountHisDao
 *
 * @author lizhifu
 * @since 2023/5/3
 */
@SpringBootTest
public class AccountHisDaoTest {
    @Resource
    AccountHisDao accountHisDao;

    @Test
    public void allTest(){
        String accountNo = "102023042380417982345";
        System.out.println("selectDeal:"+ accountHisDao.selectDeal(accountNo));
    }
}
