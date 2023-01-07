package com.tomato.account.service;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.constant.AccountStatusEnum;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.domain.exception.BusinessException;

/**
 * 账户校验
 *
 * @author lizhifu
 * @since 2023/1/7
 */
public class AccountCheckService {
    /**
     * 检查账户是否存在
     */
    public static void checkAccountExist(AccountInfoEntity accountInfoEntity){
        if(accountInfoEntity == null){
            throw new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST);
        }
    }
    /**
     * 检查账户是否存在
     */
    public static void checkAccountNotExist(AccountInfoEntity accountInfoEntity){
        if(accountInfoEntity != null){
            throw new BusinessException(AccountRespCode.ACCOUNT_ALREADY_EXIST);
        }
    }
    /**
     * 是否可以收款
     */
    public static void checkCredit(String accountStatus){
        if (accountStatus.equals(AccountStatusEnum.ACCOUNT_AVAILABLE.getValue()) || accountStatus.equals(AccountStatusEnum.ACCOUNT_FREEZE_DEBIT.getValue())){
             return;
        }else {
            throw new BusinessException(AccountRespCode.ACCOUNT_STATUS_NOT_ACTIVE);
        }
    }
}
