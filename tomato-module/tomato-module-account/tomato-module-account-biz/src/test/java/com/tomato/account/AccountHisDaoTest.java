package com.tomato.account;

import com.tomato.account.constant.AccountHisTypeEnum;
import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.manager.AccountHisManager;
import com.tomato.domain.type.YesNoTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * AccountHisDao
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@SpringBootTest
public class AccountHisDaoTest {
    @Resource
    AccountHisDao accountHisDao;
    @Resource
    AccountHisManager accountHisManager;
    @Test
    public void all(){
        String accountNo = "102023010145340502001";
        Long accountHisId = 4279550717954L;

        AccountHisEntity accountHisEntity = new AccountHisEntity();
        accountHisEntity.setAccountNo(accountNo);
//        accountHisEntity.setAccountStatus(AccountStatusEnum.DEAL.getValue());
        accountHisEntity.setAccountHisType(AccountHisTypeEnum.SETTLEMENT.getValue());
        accountHisEntity.setAmount(new BigDecimal(100));
        accountHisEntity.setThirdNo(UUID.randomUUID().toString());
        accountHisEntity.setAllowSett(YesNoTypeEnum.YES.getValue());
        accountHisManager.insert(accountHisEntity);

        System.out.println(accountHisDao.selectByAccountHisId(accountHisId, accountNo));
        System.out.println(accountHisDao.selectByThirdNo(accountNo, "d27bb021-5762-4fae-871a-b704a69385d0"));
        System.out.println(accountHisDao.checkThirdNo(accountNo, "d27bb021-5762-4fae-871a-b704a69385d0"));
    }
}
