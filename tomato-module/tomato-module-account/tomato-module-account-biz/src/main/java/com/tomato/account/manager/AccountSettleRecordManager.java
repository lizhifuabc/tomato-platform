package com.tomato.account.manager;

import com.tomato.account.dao.AccountSettleRecordDao;
import com.tomato.account.domain.entity.*;
import com.tomato.account.vo.enums.AccountHisTypeEnum;
import com.tomato.common.exception.BusinessException;
import com.tomato.common.enums.CommonStatusEnum;
import com.tomato.common.util.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 账户结算记录
 *
 * @author lizhifu
 * @since 2023/1/11
 */
@Service
@Slf4j
public class AccountSettleRecordManager {
    private final AccountSettleRecordDao accountSettleRecordDao;
    private final AccountRateManager accountRateManager;
    public AccountSettleRecordManager(AccountSettleRecordDao accountSettleRecordDao, AccountRateManager accountRateManager) {
        this.accountSettleRecordDao = accountSettleRecordDao;
        this.accountRateManager = accountRateManager;
    }
    public AccountSettleRecordEntity create(AccountSettleControlEntity accountSettleControl,
                                             AccountInfoEntity accountInfoEntity){
        LocalDate settleDate = accountSettleControl.getNextSettleDate();
        AccountSettleRecordEntity dao = accountSettleRecordDao.selectByAccountNoAndSettleDate(accountSettleControl.getAccountNo(),settleDate);
        if (dao != null) {
            log.error("账号[{}]:[{}]已结算",accountInfoEntity.getAccountNo(),settleDate);
            throw new BusinessException("今日已结算");
        }
        // 风险预存期外余额更新时间 < 结算日期，不允许结算
        if(accountInfoEntity.getOutReserveDate().isBefore(settleDate)){
            log.error("账号[{}]:[{}]尚未更新风险预存期外余额:[{}]",accountInfoEntity.getAccountNo(),settleDate,accountInfoEntity.getAccountNo());
            throw new BusinessException("今日尚未更新风险预存期外余额");
        }
        return createSuccess(accountSettleControl,accountInfoEntity,settleDate);
    }
    private AccountSettleRecordEntity createFail(AccountSettleControlEntity accountSettleControl,LocalDate settleDate,String settleRemark){
        AccountSettleRecordEntity accountSettleRecordEntity = createInit(accountSettleControl,settleDate);
        accountSettleRecordEntity.setSettleStatus(CommonStatusEnum.FAIL.getValue());
        accountSettleRecordEntity.setSettleAmount(BigDecimal.ZERO);
        accountSettleRecordEntity.setSettleRate(BigDecimal.ZERO);
        accountSettleRecordEntity.setSettleFee(BigDecimal.ZERO);
        accountSettleRecordEntity.setSettleRemark(settleRemark);
        // 插入
        accountSettleRecordDao.insert(accountSettleRecordEntity);
        return accountSettleRecordEntity;
    }

    private AccountSettleRecordEntity createInit(AccountSettleControlEntity accountSettleControl,LocalDate settleDate){
        AccountSettleRecordEntity settleRecord = new AccountSettleRecordEntity();
        settleRecord.setAccountNo(accountSettleControl.getAccountNo());
        settleRecord.setMerchantNo(accountSettleControl.getMerchantNo());
        settleRecord.setAccountSettleId(accountSettleControl.getAccountSettleId());
        // 这里不能使用结算控制的时间，防止数据已经被其他更新
        settleRecord.setSettleDate(settleDate);
        return settleRecord;
    }

    private AccountSettleRecordEntity createSuccess(AccountSettleControlEntity accountSettleControl, AccountInfoEntity accountInfoEntity, LocalDate settleDate){
        AccountSettleRecordEntity accountSettleRecordEntity = createInit(accountSettleControl,settleDate);
        accountSettleRecordEntity.setSettleStatus(CommonStatusEnum.SUCCESS.getValue());
        // 计算手续费
        AccountRateEntity rate = accountRateManager.getRate(accountInfoEntity.getAccountNo(), AccountHisTypeEnum.SETTLEMENT.getValue()).orElseThrow(() -> new BusinessException("账户费率不存在"));
        // 基础手续费
        accountSettleRecordEntity.setSettleAmount(accountInfoEntity.getOutReserveBalance());
        accountSettleRecordEntity.setSettleRate(rate.getRate());
        // 计算手续费
        BigDecimal settleFee = BigDecimalUtil.multiply(accountInfoEntity.getOutReserveBalance(), accountSettleRecordEntity.getSettleRate());
        accountSettleRecordEntity.setSettleFee(settleFee);
        // 插入
        accountSettleRecordDao.insert(accountSettleRecordEntity);
        return accountSettleRecordEntity;
    }
}
