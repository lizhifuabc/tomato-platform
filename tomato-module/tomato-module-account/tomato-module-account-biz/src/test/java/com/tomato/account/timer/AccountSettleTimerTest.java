package com.tomato.account.timer;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleControlEntity;
import com.tomato.account.enums.AccountTypeEnum;
import com.tomato.account.service.AccountSettleService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * AccountSettleTimer
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@SpringBootTest
public class AccountSettleTimerTest {
    @Resource
    AccountSettleTimer accountSettleTimer;
    @Resource
    AccountOutReserveBalanceTimer accountOutReserveBalanceTimer;
    @Resource
    AccountSettleService accountSettleService;
    @Resource
    AccountSettleControlDao accountSettleControlDao;
    @Resource
    AccountInfoDao accountInfoDao;
    @Test
    public void test(){
        String merchantNo = "1234";
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByMerchantNo(merchantNo, AccountTypeEnum.SETTLEMENT.getValue());
        AccountSettleControlEntity accountSettleControlEntity = accountSettleControlDao.selectByAccountNo(accountInfoEntity.getAccountNo());
        accountSettleTimer.run();
    }
}
