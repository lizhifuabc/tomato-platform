package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.enums.AccountTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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
        LocalDate exe = LocalDate.of(2023,1,17);
        String merchantNo = "10202301010004121";
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByMerchantNo(merchantNo, AccountTypeEnum.SETTLEMENT.getValue());
        accountOutReserveBalanceService.exe(accountInfoEntity.getAccountNo(),exe);
    }
}
