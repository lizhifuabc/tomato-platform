package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountTradReq;
import com.tomato.account.enums.AccountTypeEnum;
import com.tomato.account.service.AccountTradService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * AccountTradService
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@SpringBootTest
public class AccountTradServiceTest {
    @Resource
    AccountTradService accountTradService;
    @Resource
    AccountInfoDao accountInfoDao;
    @Test
    public void test(){
        String merchantNo = "10202301010004121";
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByMerchantNo(merchantNo, AccountTypeEnum.SETTLEMENT.getValue());
        AccountTradReq accountTradReq = new AccountTradReq();
        for (int i = 0; i < 10; i++) {
            accountTradReq.setAccountNo(accountInfoEntity.getAccountNo());
            accountTradReq.setMerchantNo(merchantNo);
            accountTradReq.setAccountType("SETTLEMENT");
            accountTradReq.setAccountHisType("SETTLEMENT");
            accountTradReq.setAmount(new BigDecimal(100000));
            accountTradReq.setThirdNo(UUID.randomUUID().toString());
            accountTradReq.setMerchantOrderNo(UUID.randomUUID().toString());
            accountTradService.add(accountTradReq);

            accountTradReq.setThirdNo(UUID.randomUUID().toString());
            accountTradService.addAsync(accountTradReq);
        }

        accountTradReq.setThirdNo(UUID.randomUUID().toString());
        accountTradReq.setAmount(new BigDecimal(-600));
        accountTradService.deduct(accountTradReq);

        accountTradReq.setThirdNo(UUID.randomUUID().toString());
        accountTradReq.setAmount(new BigDecimal(-600));
        accountTradService.deductAsync(accountTradReq);
    }
}
