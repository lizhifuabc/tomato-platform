package com.tomato.account;

import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.account.domain.req.AccountSettleCreateReq;
import com.tomato.account.domain.req.AccountSettleManagerCreateReq;
import com.tomato.account.enums.AccountTypeEnum;
import com.tomato.account.enums.CycleTypeEnum;
import com.tomato.account.enums.SettleTargetTypeEnum;
import com.tomato.account.enums.SettleTypeEnum;
import com.tomato.account.service.AccountOperateService;
import com.tomato.account.service.AccountSettleOperateService;
import com.tomato.domain.core.enums.YesNoTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * Account
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@SpringBootTest
public class AccountFlowTest {
    @Resource
    AccountOperateService accountOperateService;
    @Resource
    AccountSettleOperateService accountSettleOperateService;
    @Test
    public void test(){
        // 创建账户
        AccountCreateReq accountCreateReq = new AccountCreateReq();
        accountCreateReq.setAccountType(AccountTypeEnum.SETTLEMENT.getValue());
        accountCreateReq.setMerchantNo("10202301010004121");
        AccountInfoEntity account = accountOperateService.createAccount(accountCreateReq);
        // 创建结算
        AccountSettleManagerCreateReq accountSettleManagerCreateReq = new AccountSettleManagerCreateReq();
        accountSettleManagerCreateReq.setAccountNo(account.getAccountNo());
        accountSettleManagerCreateReq.setMerchantNo(account.getMerchantNo());
        AccountSettleCreateReq accountSettleCreateReq = new AccountSettleCreateReq();
        accountSettleCreateReq.setSettleType(SettleTypeEnum.AUTO_SETTLEMENT.getValue());
        accountSettleCreateReq.setCycleType(CycleTypeEnum.WEEK.getValue());
        accountSettleCreateReq.setCycleData("1,2,3,4,6,7");
        accountSettleCreateReq.setReserveDays(1);
        accountSettleCreateReq.setMinAmount(new BigDecimal(100));
        accountSettleCreateReq.setSettleFeeFlag(YesNoTypeEnum.YES.getValue());
        accountSettleCreateReq.setSettleRate(new BigDecimal(0.06));
        accountSettleCreateReq.setLimitSettleFee(new BigDecimal(100));
        accountSettleCreateReq.setMaxSettleFee(new BigDecimal(200));
        accountSettleCreateReq.setMaxSettleDays(100);

        accountSettleCreateReq.setRemark("备注而是备注而是备注而是");
        accountSettleCreateReq.setSettleTargetType(SettleTargetTypeEnum.BANK_CARD.getValue());

        accountSettleManagerCreateReq.setAccountSettleCreateReq(accountSettleCreateReq);
        accountSettleOperateService.create(accountSettleManagerCreateReq);
    }
}
