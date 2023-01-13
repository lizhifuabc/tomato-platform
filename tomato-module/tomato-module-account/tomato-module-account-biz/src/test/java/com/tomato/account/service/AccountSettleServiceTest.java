package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleControlEntity;
import com.tomato.account.enums.AccountTypeEnum;
import com.tomato.util.thread.ThreadUtil;
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
    @Resource
    AccountSettleControlDao accountSettleControlDao;
    @Resource
    AccountInfoDao accountInfoDao;
    @Test
    public void settle(){
        LocalDate nextSettleDate = LocalDate.of(2023,1,17);
        String merchantNo = "10202301010004121";
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByMerchantNo(merchantNo, AccountTypeEnum.SETTLEMENT.getValue());
        AccountSettleControlEntity accountSettleControlEntity = accountSettleControlDao.selectByAccountNo(accountInfoEntity.getAccountNo());

        accountSettleService.settle(nextSettleDate,accountSettleControlEntity);

        ThreadUtil.sleep(1000);
    }
}