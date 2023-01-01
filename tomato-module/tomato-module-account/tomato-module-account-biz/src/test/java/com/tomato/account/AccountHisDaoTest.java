package com.tomato.account;

import com.tomato.account.constant.AccountHisTypeEnum;
import com.tomato.account.constant.AccountStatusEnum;
import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.entity.AccountHisEntity;
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

    @Test
    public void all(){
        String accountNo = "102023010145340502001";
        Long accountHisId = 100L;

        AccountHisEntity accountHisEntity = new AccountHisEntity();
        accountHisEntity.setAccountHisId(accountHisId);
        accountHisEntity.setAccountNo(accountNo);
        accountHisEntity.setAccountStatus(AccountStatusEnum.DEAL.getValue());
        accountHisEntity.setAccountHisType(AccountHisTypeEnum.SETTLEMENT.getValue());
        accountHisEntity.setAmount(new BigDecimal(100));
        accountHisEntity.setThirdNo(UUID.randomUUID().toString());
        accountHisEntity.setAllowSett(YesNoTypeEnum.YES.getValue());
        accountHisDao.insert(accountHisEntity);

        System.out.println(accountHisDao.selectByAccountHisId(accountHisId, accountNo));
    }
}
