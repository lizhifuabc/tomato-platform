package com.tomato.account.manager;

import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountSettleCreateReq;
import com.tomato.account.enums.CycleTypeEnum;
import com.tomato.account.enums.SettleTargetTypeEnum;
import com.tomato.account.enums.SettleTypeEnum;
import com.tomato.account.manager.AccountSettleManager;
import com.tomato.domain.type.YesNoTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * AccountSettleService
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@SpringBootTest
public class AccountSettleManagerTest {
    @Resource
    AccountSettleManager accountSettleManager;
    @Test
    public void test(){
        AccountInfoEntity accountInfo = new AccountInfoEntity();
        accountInfo.setAccountNo("12312");
        accountInfo.setMerchantNo("12312");

        AccountSettleCreateReq accountSettleCreateReq = new AccountSettleCreateReq();
        accountSettleCreateReq.setSettleType(SettleTypeEnum.AUTO_SETTLEMENT.getValue());
        accountSettleCreateReq.setCycleType(CycleTypeEnum.WEEK.getValue());
        accountSettleCreateReq.setCycleData("1,2,3,4,8,6,7");
        accountSettleCreateReq.setReserveDays(3);
        accountSettleCreateReq.setMinAmount(new BigDecimal(100));
        accountSettleCreateReq.setSettleFeeFlag(YesNoTypeEnum.YES.getValue());
        accountSettleCreateReq.setSettlRate(new BigDecimal(0.06));
        accountSettleCreateReq.setLimitSettleFee(new BigDecimal(100));
        accountSettleCreateReq.setMaxSettleFee(new BigDecimal(200));
        accountSettleCreateReq.setMaxSettleDays(100);

        accountSettleCreateReq.setSettleTargetType(SettleTargetTypeEnum.BANK_CARD.getValue());
        accountSettleCreateReq.setUrgentFlag(YesNoTypeEnum.YES.getValue());
        accountSettleCreateReq.setAutoRemitFlag(YesNoTypeEnum.YES.getValue());
        accountSettleManager.create(accountSettleCreateReq,accountInfo);
    }
}
