package com.tomato.account.manager;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.dao.AccountSettleDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleControlEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.enums.AccountTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * AccountSettleRecordManager
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@SpringBootTest
public class AccountSettleRecordManagerTest {
    @Resource
    AccountSettleRecordManager accountSettleRecordManager;
    @Resource
    AccountSettleControlDao accountSettleControlDao;
    @Resource
    AccountInfoDao accountInfoDao;
    @Resource
    AccountSettleDao accountSettleDao;
    @Test
    public void test(){
        String merchantNo = "10202301010004121";
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByMerchantNo(merchantNo, AccountTypeEnum.SETTLEMENT.getValue());
        AccountSettleEntity accountSettleEntity = accountSettleDao.selectByAccountNo(accountInfoEntity.getAccountNo());
        AccountSettleControlEntity accountSettleControl = accountSettleControlDao.selectById(1L);
        accountSettleRecordManager.create(accountSettleControl,accountInfoEntity,LocalDate.now());
    }
}
