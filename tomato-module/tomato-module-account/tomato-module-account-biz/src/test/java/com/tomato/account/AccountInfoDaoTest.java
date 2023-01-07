package com.tomato.account;

import com.tomato.account.dao.AccountInfoDao;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * AccountDao
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@SpringBootTest
public class AccountInfoDaoTest {
    @Resource
    AccountInfoDao accountInfoDao;

    @Test
    public void allTest(){
        String accountNo = "102023010145340502001";
        String merchantNo = "10202301010002001";
        System.out.println("selectByAccountNo:"+ accountInfoDao.selectByAccountNo(accountNo));
        System.out.println("selectByMerchantNo:"+ accountInfoDao.selectByMerchantNo(merchantNo,"12"));


        System.out.println("freeze:"+ accountInfoDao.freeze(accountNo, new BigDecimal(100), 1));
        System.out.println("unfreeze:"+ accountInfoDao.unfreeze(accountNo, new BigDecimal(100), 2));
    }
}
