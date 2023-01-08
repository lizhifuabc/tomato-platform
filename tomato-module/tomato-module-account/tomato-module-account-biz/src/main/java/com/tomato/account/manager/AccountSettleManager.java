package com.tomato.account.manager;

import com.tomato.account.dao.AccountSettleDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.domain.req.AccountSettleCreateReq;
import com.tomato.account.enums.SettleTargetTypeEnum;
import com.tomato.account.enums.SettleTypeEnum;
import com.tomato.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
@Slf4j
public class AccountSettleManager {
    private final AccountSettleDao accountSettleDao;

    public AccountSettleManager(AccountSettleDao accountSettleDao) {
        this.accountSettleDao = accountSettleDao;
    }

    /**
     * 创建账户结算规则
     * @param accountSettleCreateReq 账户结算规则
     */
    public AccountSettleEntity create(AccountSettleCreateReq accountSettleCreateReq, AccountInfoEntity accountInfo){
        AccountSettleEntity accountSettleEntity = BeanUtil.copy(accountSettleCreateReq,AccountSettleEntity.class);
        accountSettleEntity.setAccountNo(accountInfo.getAccountNo());
        accountSettleEntity.setMerchantNo(accountInfo.getMerchantNo());
        // 自动结算（定期结算）
        if(accountSettleEntity.getSettleType().equals(SettleTypeEnum.AUTO_SETTLEMENT.getValue())){
            // 升序
            String[] cycleData = accountSettleEntity.getCycleData().split(",");
            Arrays.sort(cycleData);
            accountSettleEntity.setCycleData(StringUtils.join(cycleData,","));
        }
        // 默认最大结算天数 Integer.MAX_VALUE
        if(accountSettleEntity.getMaxSettleDays() == null || accountSettleEntity.getMaxSettleDays() == 0){
            accountSettleEntity.setMaxSettleDays(Integer.MAX_VALUE);
        }
        // 默认结算到目标账户类型为银行卡
        if(StringUtils.isBlank(accountSettleEntity.getSettleTargetType())){
            accountSettleEntity.setSettleTargetType(SettleTargetTypeEnum.BANK_CARD.getValue());
        }
        // 保存结算规则
        accountSettleEntity.setCreateTime(LocalDateTime.now());
        accountSettleDao.insert(accountSettleEntity);
        log.info("设置结算规则, merchantNo:{}, id:{}", accountSettleEntity.getMerchantNo(),accountSettleEntity.getId());
        return accountSettleEntity;
    }
}
