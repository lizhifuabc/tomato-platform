package com.tomato.account.manager;

import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.domain.entity.AccountSettleControlEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.domain.entity.AccountSettleRecordEntity;
import com.tomato.account.enums.CycleTypeEnum;
import com.tomato.account.enums.SettleTypeEnum;
import com.tomato.account.service.AccountWorkService;
import com.tomato.account.util.SettleDayUtil;
import com.tomato.domain.core.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 账户结算控制
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
public class AccountSettleControlManager {
    private final AccountSettleControlDao accountSettleControlDao;
    private final AccountWorkService accountWorkService;
    public AccountSettleControlManager(AccountSettleControlDao accountSettleControlDao, AccountWorkService accountWorkService) {
        this.accountSettleControlDao = accountSettleControlDao;
        this.accountWorkService = accountWorkService;
    }

    /**
     * 创建账户结算控制
     * 下次结算日 = 当前时间 - 1 - 风险控制日期
     * @param accountSettleEntity 结算数据
     */
    public void create(AccountSettleEntity accountSettleEntity){

        AccountSettleControlEntity accountSettleControlEntity = new AccountSettleControlEntity();
        accountSettleControlEntity.setAccountSettleId(accountSettleEntity.getId());
        accountSettleControlEntity.setAccountNo(accountSettleEntity.getAccountNo());
        accountSettleControlEntity.setMerchantNo(accountSettleEntity.getMerchantNo());
        // 自动结算（定期结算）
        if (SettleTypeEnum.AUTO_SETTLEMENT.getValue().equals(accountSettleEntity.getSettleType())) {
            CycleTypeEnum cycleTypeEnum = CycleTypeEnum.fromValue(accountSettleEntity.getCycleType());
            // 结算周期计算
            accountSettleControlEntity.setNextSettleDate(
                    SettleDayUtil.settleDate(
                            accountSettleEntity.getCycleData().split(","),
                            accountSettleEntity.getCreateTime().toLocalDate(),
                            cycleTypeEnum,
                            accountSettleEntity.getReserveDays()
                    )
            );
            // 是否节假日
            accountSettleControlEntity.setNextSettleDate(accountWorkService.nextWorkDay(accountSettleControlEntity.getNextSettleDate(),cycleTypeEnum));
        }
        accountSettleControlDao.insert(accountSettleControlEntity);
    }

    /**
     * 更新账户结算控制:计算下一个结算日
     * @param accountSettleEntity
     * @param accountSettleControlEntity
     * @param accountSettleRecordEntity
     */
    public void updateSettleControl(AccountSettleEntity accountSettleEntity,
                                    AccountSettleControlEntity accountSettleControlEntity,
                                    AccountSettleRecordEntity accountSettleRecordEntity){
        // 结算周期计算
        CycleTypeEnum cycleTypeEnum = CycleTypeEnum.fromValue(accountSettleEntity.getCycleType());
        LocalDate nextSettleDate = SettleDayUtil.settleDate(
                accountSettleEntity.getCycleData().split(","),
                accountSettleControlEntity.getNextSettleDate(),
                cycleTypeEnum,
                accountSettleEntity.getReserveDays()
        );
        // 是否节假日
        nextSettleDate = accountWorkService.nextWorkDay(nextSettleDate,cycleTypeEnum);

        int count = accountSettleControlDao.updateSettleControl(
                accountSettleControlEntity.getId(),
                nextSettleDate,
                accountSettleRecordEntity.getId(),
                accountSettleControlEntity.getVersion()
        );
        if(count <= 0){
            throw new BusinessException("在更新结算控制的时候出现乐观锁异常");
        }
    }
}
