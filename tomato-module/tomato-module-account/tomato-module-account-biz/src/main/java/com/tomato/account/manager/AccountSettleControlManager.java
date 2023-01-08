package com.tomato.account.manager;

import com.tomato.account.dao.AccountSettleControlDao;
import com.tomato.account.domain.entity.AccountSettleControlEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.enums.SettleTypeEnum;
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

    public AccountSettleControlManager(AccountSettleControlDao accountSettleControlDao) {
        this.accountSettleControlDao = accountSettleControlDao;
    }
    public void create(AccountSettleEntity accountSettleEntity){

        AccountSettleControlEntity accountSettleControlEntity = new AccountSettleControlEntity();
        accountSettleControlEntity.setAccountSettleId(accountSettleEntity.getId());
        accountSettleControlEntity.setAccountNo(accountSettleEntity.getAccountNo());
        accountSettleControlEntity.setMerchantNo(accountSettleEntity.getMerchantNo());

        if (SettleTypeEnum.AUTO_SETTLEMENT.getValue().equals(accountSettleEntity.getSettleType())) {
            accountSettleControlEntity.setNextSettleDate(
                    SettleDayUtil.settleDate(
                            accountSettleEntity.getCycleData().split(","),
                            accountSettleEntity.getCreateTime().toLocalDate(),
                            accountSettleEntity.getCycleType(),
                            accountSettleEntity.getReserveDays()
                    )
            );
        }
        // 给新商户新增上上次汇总日期，上次汇总日期为入网的前一天，因为在汇总的时候取的是上次汇总日期+1
        accountSettleControlEntity.setLastCollectTime(accountSettleEntity.getCreateTime().minusDays(1));
        accountSettleControlDao.insert(accountSettleControlEntity);
    }
}
