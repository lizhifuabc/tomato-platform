package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountSettleCreateReq;
import com.tomato.account.domain.req.AccountSettleManagerCreateReq;
import com.tomato.account.enums.AccountTypeEnum;
import com.tomato.account.enums.CycleTypeEnum;
import com.tomato.account.enums.SettleTargetTypeEnum;
import com.tomato.account.enums.SettleTypeEnum;
import com.tomato.domain.type.YesNoTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * AccountSettleManagerService
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@SpringBootTest
public class AccountSettleManagerServiceTest {
    @Resource
    AccountSettleManagerService accountSettleManagerService;
    @Resource
    AccountInfoDao accountInfoDao;
    @Test
    public void test(){
        String merchantNo = "10202301010004121";
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByMerchantNo(merchantNo, AccountTypeEnum.SETTLEMENT.getValue());

        AccountSettleManagerCreateReq accountSettleManagerCreateReq = new AccountSettleManagerCreateReq();
        accountSettleManagerCreateReq.setAccountNo(accountInfoEntity.getAccountNo());
        accountSettleManagerCreateReq.setMerchantNo(merchantNo);
        AccountSettleCreateReq accountSettleCreateReq = new AccountSettleCreateReq();
        accountSettleCreateReq.setSettleType(SettleTypeEnum.AUTO_SETTLEMENT.getValue());
        accountSettleCreateReq.setCycleType(CycleTypeEnum.WEEK.getValue());
        accountSettleCreateReq.setCycleData("1,2,3,4,8,6,7");
        accountSettleCreateReq.setReserveDays(0);
        accountSettleCreateReq.setMinAmount(new BigDecimal(100));
        accountSettleCreateReq.setSettleFeeFlag(YesNoTypeEnum.YES.getValue());
        accountSettleCreateReq.setLimitSettleFee(new BigDecimal(100));
        accountSettleCreateReq.setMaxSettleFee(new BigDecimal(200));
        accountSettleCreateReq.setMaxSettleDays(100);
        accountSettleCreateReq.setRemark("测试数据");

        accountSettleCreateReq.setSettleTargetType(SettleTargetTypeEnum.BANK_CARD.getValue());

        accountSettleManagerCreateReq.setAccountSettleCreateReq(accountSettleCreateReq);
        accountSettleManagerService.create(accountSettleManagerCreateReq);
    }
}
