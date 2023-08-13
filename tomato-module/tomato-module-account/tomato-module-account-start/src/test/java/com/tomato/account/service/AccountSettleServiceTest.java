package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleControlEntity;
import com.tomato.account.vo.enums.AccountTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        String merchantNo = "1234";
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByMerchantNo(merchantNo, AccountTypeEnum.SETTLEMENT.getValue());
        AccountSettleControlEntity accountSettleControlEntity = accountSettleControlDao.selectByAccountNo(accountInfoEntity.getAccountNo());

        accountSettleService.settle(accountSettleControlEntity.getAccountNo());
    }
}
