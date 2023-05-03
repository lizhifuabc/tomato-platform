package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountSettleDao;
import com.tomato.account.domain.dto.AccountTradDto;
import com.tomato.account.domain.entity.*;
import com.tomato.account.enums.AccountHisTypeEnum;
import com.tomato.account.manager.AccountSettleControlManager;
import com.tomato.account.manager.AccountSettleRecordManager;
import com.tomato.account.service.trad.AccountTradService;
import com.tomato.common.enums.CommonStatusEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private final AccountSettleControlManager accountSettleControlManager;
    @Resource(name = "accountDeductService")
    private AccountTradService accountTradService;
    public AccountSettleService(AccountSettleRecordManager accountSettleRecordManager, AccountInfoDao accountInfoDao, AccountSettleDao accountSettleDao, AccountSettleControlManager accountSettleControlManager) {
        this.accountSettleRecordManager = accountSettleRecordManager;
        this.accountInfoDao = accountInfoDao;
        this.accountSettleDao = accountSettleDao;
        this.accountSettleControlManager = accountSettleControlManager;
    }

    /**
     * 账户结算
     * @param accountNo 账户编号
     * @return 下次结算日
     */
    @Transactional(rollbackFor = Exception.class)
    public LocalDate settle(String accountNo){
        log.info("账户结算：开始：账户：{}，日期：{}",accountNo, LocalDateTime.now());
        // 查询账户结算控制
        AccountSettleControlEntity accountSettleControl = accountSettleControlManager.selectByAccountNo(accountNo).orElseThrow(()->new RuntimeException("账户结算控制不存在"));
        // 下次结算日期大于当前日期，证明已经结算过了,不结算
        if (accountSettleControl.getNextSettleDate().isAfter(LocalDate.now())){
            log.error("账户结算：结束：账户：{}，账户已经结算，下次结算日期：{}",accountNo, accountSettleControl.getNextSettleDate());
            return accountSettleControl.getNextSettleDate();
        }
        // 查询账户信息
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByAccountNo(accountSettleControl.getAccountNo());
        // 结算信息
        AccountSettleEntity accountSettleEntity = accountSettleDao.selectByAccountNo(accountSettleControl.getAccountNo());
        // 创建结算记录
        AccountSettleRecordEntity accountSettleRecordEntity = accountSettleRecordManager.create(accountSettleControl, accountInfoEntity);
        // 更新结算控制：账户结算记录ID、下次结算日期
        LocalDate nextSettleDate = accountSettleControlManager.updateSettleControl(accountSettleEntity,accountSettleControl,accountSettleRecordEntity);
        // 结算金额 > 0 && 结算状态为 SUCCESS
        if (accountSettleRecordEntity.getSettleAmount().compareTo(BigDecimal.ZERO) > 0
                && accountSettleRecordEntity.getSettleStatus().equals(CommonStatusEnum.SUCCESS.getValue())){
            // 同步扣款
            AccountTradDto accountTradDto = new AccountTradDto();
            accountTradDto.setAccountNo(accountNo);
            accountTradDto.setRemark("自动结算扣款");
            accountTradDto.setAmount(accountSettleRecordEntity.getSettleAmount().add(accountSettleRecordEntity.getSettleFee()).negate());
            accountTradDto.setAccountHisType(AccountHisTypeEnum.SETTLEMENT.getValue());
            String no = "SETTLE:" + accountSettleRecordEntity.getAccountNo() + ":" + accountSettleRecordEntity.getId();
            accountTradDto.setSysNo(no);
            accountTradDto.setMerchantOrderNo(no);
            accountTradService.exe(accountTradDto);
            // TODO 打款
        }
        return nextSettleDate;
    }
}
