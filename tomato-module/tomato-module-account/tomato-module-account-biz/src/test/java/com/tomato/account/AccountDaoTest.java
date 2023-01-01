package com.tomato.account;

import com.tomato.account.dao.AccountDao;
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
public class AccountDaoTest {
    @Resource
    AccountDao accountDao;

    @Test
    public void allTest(){
        String accountNo = "102023010145340502001";
        String merchantNo = "10202301010002001";
        System.out.println("selectByAccountNo:"+accountDao.selectByAccountNo(accountNo));
        System.out.println("selectByMerchantNo:"+accountDao.selectByMerchantNo(merchantNo, 1));
        System.out.println("selectByMerchantNoWithOutStatus:"+accountDao.selectByMerchantNoWithOutStatus(merchantNo));

        System.out.println("deduct:"+accountDao.deduct(accountNo, new BigDecimal(100), 0));
        System.out.println("add:"+accountDao.add(accountNo, new BigDecimal(100), 0));

        System.out.println("freeze:"+accountDao.freeze(accountNo, new BigDecimal(100), 1));
        System.out.println("unfreeze:"+accountDao.unfreeze(accountNo, new BigDecimal(100), 2));
    }
}
