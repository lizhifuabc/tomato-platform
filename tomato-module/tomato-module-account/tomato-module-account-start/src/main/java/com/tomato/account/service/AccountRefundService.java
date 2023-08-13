package com.tomato.account.service;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.dao.AccountHisDao;
import com.tomato.account.domain.bo.AccountBalanceBO;
import com.tomato.account.domain.bo.AccountHisBO;
import com.tomato.account.domain.bo.AccountRefundBO;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.vo.enums.AccountHisTypeEnum;
import com.tomato.account.manager.AccountHisManager;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 账户退款
 * 1. 退款直接退入风险外金额
 * 2. 是否存在风内金额出款的操作 TODO
 * @author lizhifu
 * @since 2023/1/11
 */
@Service
@Slf4j
public class AccountRefundService {
    private final AccountHisDao accountHisDao;
    private final AccountHisManager accountHisManager;
    private final AccountInfoManager accountInfoManager;
    public AccountRefundService(AccountHisDao accountHisDao, AccountHisManager accountHisManager, AccountInfoManager accountInfoManager) {
        this.accountHisDao = accountHisDao;
        this.accountHisManager = accountHisManager;
        this.accountInfoManager = accountInfoManager;
    }

    /**
     * 结算退款
     * <p>1. 结算退款，此时退到风外余额中</p>
     *
     * @param accountRefundBO 退款
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void settleRefund(AccountRefundBO accountRefundBO){
        log.info("账户发起退款start:[{}]", accountRefundBO);
        AccountHisEntity accountHisEntity = accountHisDao.selectBySysNo(accountRefundBO.getMerchantNo(), accountRefundBO.getOrgThirdNo());
        // 原账户历史不存在，此时存在风险
        if (Objects.isNull(accountHisEntity)){
            log.error("原账户历史不存在[{}]",accountRefundBO);
            throw new BusinessException("原账户历史不存在");
        }
        // 原账户历史类型错误
        if(!accountHisEntity.getAccountHisType().equals(AccountHisTypeEnum.SETTLEMENT.getValue())){
            log.error("原账户历史类型错误[{}]",accountRefundBO);
            throw new BusinessException("原账户历史类型错误");
        }
        // 查询账户信息
        AccountInfoEntity accountInfoEntity = accountInfoManager.selectByAccountNo(accountHisEntity.getAccountNo()).orElseThrow(()-> new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST));
        // 账户加款
        AccountBalanceBO accountBalanceBO = new AccountBalanceBO();
        accountBalanceBO.setAccountNo(accountInfoEntity.getAccountNo());
        accountBalanceBO.setVersion(accountInfoEntity.getVersion());
        // 退款金额 = 手续费 + 发生金额
        accountBalanceBO.setAmount(accountHisEntity.getAmount().add(accountHisEntity.getAmountFree()));
        accountInfoManager.settleRefund(accountBalanceBO,accountInfoEntity);
        // 创建账户历史
        AccountHisBO accountHisBO = new AccountHisBO();
        accountHisBO.setAccountNo(accountInfoEntity.getAccountNo());
        accountHisBO.setSysNo(AccountHisTypeEnum.REFUND.getValue() + ":" + accountRefundBO.getOrgThirdNo());
        accountHisBO.setAccountHisType(AccountHisTypeEnum.REFUND.getValue());
        accountHisBO.setAmount(accountBalanceBO.getAmount());
        accountHisManager.insert(accountInfoEntity,accountHisBO);

        // 更改结算记录为失败 TODO 是否更改结算控制

        log.info("账户发起退款end:[{}]", accountRefundBO);
    }
}
