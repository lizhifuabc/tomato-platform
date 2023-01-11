package com.tomato.account.manager;

import com.tomato.account.dao.AccountSettleRecordDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleControlEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.domain.entity.AccountSettleRecordEntity;
import com.tomato.domain.exception.BusinessException;
import com.tomato.domain.type.YesNoTypeEnum;
import com.tomato.util.lang.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public AccountSettleRecordManager(AccountSettleRecordDao accountSettleRecordDao) {
        this.accountSettleRecordDao = accountSettleRecordDao;
    }
    public void check(AccountSettleControlEntity accountSettleControl, AccountInfoEntity accountInfoEntity, AccountSettleEntity accountSettleEntity){
        // 风险预存期外余额更新时间 != 当前时间，即今日尚未更新风险预存期外余额
        if(!accountInfoEntity.getOutReserveTime().toLocalDate().isEqual(LocalDateTime.now().toLocalDate())){
            log.error("今日尚未更新风险预存期外余额:[{}]",accountInfoEntity.getAccountNo());
            throw new BusinessException("今日尚未更新风险预存期外余额");
        }
        // 小于最小结算金额
        if(accountInfoEntity.getOutReserveBalance().compareTo(accountSettleEntity.getMinAmount()) < 0){
            log.error("小于最小结算金额:[{}]",accountInfoEntity.getAccountNo());
            // TODO 是否插入失败结算记录
        }
    }
    public void create(AccountSettleControlEntity accountSettleControl, AccountInfoEntity accountInfoEntity, AccountSettleEntity accountSettleEntity){
        check(accountSettleControl,accountInfoEntity,accountSettleEntity);
        AccountSettleRecordEntity accountSettleRecordEntity = new AccountSettleRecordEntity();
        accountSettleRecordEntity.setAccountNo(accountSettleControl.getAccountNo());
        accountSettleRecordEntity.setMerchantNo(accountSettleControl.getMerchantNo());
        accountSettleRecordEntity.setAccountSettleId(accountSettleControl.getAccountSettleId());
        accountSettleRecordEntity.setSettleDate(LocalDate.now());
        // 计算手续费
        if (accountSettleEntity.getSettleFeeFlag().equals(YesNoTypeEnum.YES.getValue())) {
            // 基础手续费
            accountSettleRecordEntity.setSettleAmount(accountInfoEntity.getOutReserveBalance());
            accountSettleRecordEntity.setSettleRate(accountSettleEntity.getSettleRate());
            // 计算手续费
            BigDecimal settleFee = BigDecimalUtil.multiply(accountInfoEntity.getOutReserveBalance(), accountSettleRecordEntity.getSettleRate());
            accountSettleRecordEntity.setSettleFee(settleFee);
            // 手续费 >= 封顶手续费
            if(settleFee.compareTo(accountSettleEntity.getMaxSettleFee()) >= 0){
                accountSettleRecordEntity.setSettleFee(accountSettleEntity.getMaxSettleFee());
            }
            // 手续费 <= 客户不承担手续费限额 TODO 是否设置最小起征点
            if(settleFee.compareTo(accountSettleEntity.getLimitSettleFee()) >= 0){
                accountSettleRecordEntity.setSettleFee(BigDecimal.ZERO);
            }
        }
        accountSettleRecordDao.insert(accountSettleRecordEntity);
    }
}
