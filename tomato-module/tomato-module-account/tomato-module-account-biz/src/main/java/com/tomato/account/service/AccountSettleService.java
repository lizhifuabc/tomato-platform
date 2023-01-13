package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.dao.AccountSettleDao;
import com.tomato.account.domain.bo.AccountBalanceBO;
import com.tomato.account.domain.bo.AccountHisBO;
import com.tomato.account.domain.entity.*;
import com.tomato.account.enums.AccountHisTypeEnum;
import com.tomato.account.manager.AccountHisManager;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.account.manager.AccountSettleControlManager;
import com.tomato.account.manager.AccountSettleRecordManager;
import com.tomato.domain.type.CommonStatusEnum;
import com.tomato.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * 账号结算控制
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@Service
@Slf4j
public class AccountSettleService {
    private final AccountSettleRecordManager accountSettleRecordManager;
    private final AccountInfoDao accountInfoDao;
    private final AccountSettleDao accountSettleDao;
    private final AccountInfoManager accountInfoManager;
    private final AccountSettleControlManager accountSettleControlManager;
    private final AccountHisManager accountHisManager;
    public AccountSettleService(AccountSettleRecordManager accountSettleRecordManager, AccountInfoDao accountInfoDao, AccountSettleDao accountSettleDao, AccountInfoManager accountInfoManager, AccountSettleControlManager accountSettleControlManager, AccountHisManager accountHisManager) {
        this.accountSettleRecordManager = accountSettleRecordManager;
        this.accountInfoDao = accountInfoDao;
        this.accountSettleDao = accountSettleDao;
        this.accountInfoManager = accountInfoManager;
        this.accountSettleControlManager = accountSettleControlManager;
        this.accountHisManager = accountHisManager;
    }

    @Async("asyncTaskExecutorAccount")
    public void settle(LocalDate nextSettleDate,AccountSettleControlEntity accountSettleControl){
        log.info("下次结算日期等于[{}]的账户[{}]开始跑结算定时",nextSettleDate,accountSettleControl);
        // 查询账户信息
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByAccountNo(accountSettleControl.getAccountNo());
        // 结算信息
        AccountSettleEntity accountSettleEntity = accountSettleDao.selectByAccountNo(accountSettleControl.getAccountNo());

        // 创建结算记录
        AccountSettleRecordEntity accountSettleRecordEntity = accountSettleRecordManager.create(accountSettleControl, accountInfoEntity, accountSettleEntity, nextSettleDate);
        // 更新结算控制：账户结算记录ID、下次结算日期、TODO 是否需要结算状态
        accountSettleControlManager.updateSettleControl(accountSettleEntity,accountSettleControl,accountSettleRecordEntity);

        // 结算金额 > 0 && 结算状态为 SUCCESS
        if (accountSettleRecordEntity.getSettleAmount().compareTo(BigDecimal.ZERO) > 0
                && accountSettleRecordEntity.getSettleStatus().equals(CommonStatusEnum.SUCCESS.getValue())){
            // 执行账户出款
            AccountBalanceBO accountBalanceBO = new AccountBalanceBO();
            accountBalanceBO.setAccountNo(accountInfoEntity.getAccountNo());
            accountBalanceBO.setVersion(accountInfoEntity.getVersion());
            accountBalanceBO.setAmount(accountSettleRecordEntity.getSettleAmount().add(accountSettleRecordEntity.getSettleFee()));
            accountInfoManager.deduct(accountBalanceBO,accountInfoEntity);
            // 创建账户历史
            AccountHisBO accountHisBO = new AccountHisBO();
            accountHisBO.setAccountHisType(AccountHisTypeEnum.SETTLEMENT.getValue());
            accountHisBO.setAccountNo(accountSettleRecordEntity.getAccountNo());
            accountHisBO.setAmountFree(accountSettleRecordEntity.getSettleFee());
            accountHisBO.setAmount(accountSettleRecordEntity.getSettleAmount());
            // TODO 结算记录流水号
            accountHisBO.setThirdNo(accountSettleRecordEntity.getAccountNo() + accountSettleRecordEntity.getId());
            AccountHisEntity accountHisEntity = accountHisManager.insert(accountInfoEntity,accountHisBO);
        }
    }
}
