package com.tomato.account;

import com.tomato.account.constant.AccountHisTypeEnum;
import com.tomato.account.constant.AccountStatusEnum;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.manager.AccountHisManager;
import com.tomato.domain.type.YesNoTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * AccountHisManager
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@SpringBootTest
public class AccountHisManagerTest {
    @Resource
    AccountHisManager accountHisManager;

    @Test
    public void test(){
        String accountNo = "102023010145340502001";

        AccountHisEntity accountHisEntity = new AccountHisEntity();
        accountHisEntity.setAccountNo(accountNo);
        accountHisEntity.setAccountStatus(AccountStatusEnum.DEAL.getValue());
        accountHisEntity.setAccountHisType(AccountHisTypeEnum.SETTLEMENT.getValue());
        accountHisEntity.setAmount(new BigDecimal(100));
        accountHisEntity.setThirdNo(UUID.randomUUID().toString());
        accountHisEntity.setAllowSett(YesNoTypeEnum.YES.getValue());
        System.out.println(accountHisManager.insert(accountHisEntity));
    }
}
