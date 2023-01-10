package com.tomato.account.manager;

import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.domain.entity.AccountSettleControlEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.enums.CycleTypeEnum;
import com.tomato.account.enums.SettleTypeEnum;
import com.tomato.account.service.AccountWorkService;
import com.tomato.account.util.SettleDayUtil;
import org.springframework.stereotype.Service;

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
     * @param accountSettleEntity
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
        // 给新商户新增上上次汇总日期，上次汇总日期为入网的前一天，因为在汇总的时候取的是上次汇总日期+1
        accountSettleControlEntity.setLastCollectTime(accountSettleEntity.getCreateTime().minusDays(1));
        accountSettleControlDao.insert(accountSettleControlEntity);
    }
}
