package com.tomato.account.service;

import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.domain.req.AccountSettleManagerCreateReq;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.account.manager.AccountSettleControlManager;
import com.tomato.account.manager.AccountSettleManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账号结算控制
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
public class AccountSettleManagerService {
    private final AccountSettleManager accountSettleManager;
    private final AccountInfoManager accountInfoManager;
    private final AccountSettleControlManager accountSettleControlManager;

    public AccountSettleManagerService(AccountSettleManager accountSettleManager, AccountInfoManager accountInfoManager, AccountSettleControlManager accountSettleControlManager) {
        this.accountSettleManager = accountSettleManager;
        this.accountInfoManager = accountInfoManager;
        this.accountSettleControlManager = accountSettleControlManager;
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void create(AccountSettleManagerCreateReq accountSettleManagerCreateReq){
        AccountInfoEntity accountInfoEntity = accountInfoManager.selectByAccountNo(accountSettleManagerCreateReq.getAccountNo());
        // 检查账户是否存在
        AccountCheckService.checkAccountExist(accountInfoEntity);
        // 创建账户结算规则
        AccountSettleEntity accountSettleEntity = accountSettleManager.create(accountSettleManagerCreateReq.getAccountSettleCreateReq(), accountInfoEntity);
        // 创建账户结算控制
        accountSettleControlManager.create(accountSettleEntity);
    }
}
