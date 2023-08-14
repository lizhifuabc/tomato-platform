package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.vo.enums.AccountTypeEnum;
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
    @Resource
    AccountInfoDao accountInfoDao;
    @Test
    public void test(){
        String merchantNo = "1234";
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByMerchantNo(merchantNo, AccountTypeEnum.SETTLEMENT.getValue());
        accountOutReserveBalanceService.exe("102023081451860592316");
    }
}
