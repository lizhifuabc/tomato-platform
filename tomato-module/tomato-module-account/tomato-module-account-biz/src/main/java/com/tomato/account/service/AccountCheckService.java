package com.tomato.account.service;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.enums.AccountStatusTypeEnum;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.common.exception.BusinessException;

import java.math.BigDecimal;

/**
 * 账户校验
 *
 * @author lizhifu
 * @since 2023/1/7
 */
public class AccountCheckService {
    /**
     * 余额校验
     * TODO 所有的扣款都先扣风险预存期外的钱，日结通出款过程中风险预存期外的钱可能会成为负值，代扣代发、结算在扣款前已经做了金额校验
     */
    public static void checkBalance(AccountInfoEntity accountInfoEntity,BigDecimal amount){
        if(accountInfoEntity.getBalance().compareTo(amount.abs()) < 0){
            throw new BusinessException(AccountRespCode.AVAILABLE_BALANCE_NOT_ENOUGH);
        }
    }
    /**
     * 余额校验
     * TODO 所有的扣款都先扣风险预存期外的钱，日结通出款过程中风险预存期外的钱可能会成为负值，代扣代发、结算在扣款前已经做了金额校验
     */
    public static void checkOutReserveBalance(AccountInfoEntity accountInfoEntity,BigDecimal amount){
        if(accountInfoEntity.getOutReserveBalance().compareTo(amount.abs()) < 0){
            throw new BusinessException(AccountRespCode.AVAILABLE_BALANCE_NOT_ENOUGH);
        }
    }
    /**
     * 检查账户是否存在
     */
    public static void checkAccountExist(AccountInfoEntity accountInfoEntity){
        if(accountInfoEntity == null){
            throw new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST);
        }
    }
    /**
     * 是否可以收款
     */
    public static void checkAdd(String accountStatus){
        if (accountStatus.equals(AccountStatusTypeEnum.ACCOUNT_AVAILABLE.getValue()) || accountStatus.equals(AccountStatusTypeEnum.ACCOUNT_FREEZE_DEBIT.getValue())){
             return;
        }else {
            throw new BusinessException(AccountRespCode.ACCOUNT_STATUS_NOT_ACTIVE);
        }
    }
    /**
     * 是否可以收款
     */
    public static void checkDeduct(String accountStatus){
        if (accountStatus.equals(AccountStatusTypeEnum.ACCOUNT_AVAILABLE.getValue()) || accountStatus.equals(AccountStatusTypeEnum.ACCOUNT_FREEZE_CREDIT.getValue())){
            return;
        }else {
            throw new BusinessException(AccountRespCode.ACCOUNT_STATUS_NOT_ACTIVE);
        }
    }
}
