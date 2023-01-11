package com.tomato.account.dao;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
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
        String accountNo = "102023011064752054121";
        String merchantNo = "10202301010004121";
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByAccountNo(accountNo);
        System.out.println("selectByMerchantNo:"+ accountInfoDao.selectByMerchantNo(merchantNo,"12"));

        accountInfoDao.updateOutReserveBalance(accountNo,new BigDecimal(100),accountInfoEntity.getVersion());

        System.out.println("freeze:"+ accountInfoDao.freeze(accountNo, new BigDecimal(100), 1));
    }
}
