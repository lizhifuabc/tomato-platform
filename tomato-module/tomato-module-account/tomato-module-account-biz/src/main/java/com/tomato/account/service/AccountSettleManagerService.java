package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountBankCardCreateReq;
import com.tomato.account.domain.req.AccountSettleCreateReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
@Slf4j
public class AccountSettleManagerService {
    private final AccountInfoDao accountInfoDao;

    public AccountSettleManagerService(AccountInfoDao accountInfoDao) {
        this.accountInfoDao = accountInfoDao;
    }

    /**
     * 创建账户结算规则
     * @param accountSettleCreateReq 账户结算规则
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void create(AccountSettleCreateReq accountSettleCreateReq, AccountBankCardCreateReq accountBankCardCreateReq){
        AccountInfoEntity account = accountInfoDao.selectByAccountNo(accountSettleCreateReq.getAccountNo());
        // 1.保存结算规则，初始化结算控制
        // 2.结算目标银行卡
        log.info("设置结算规则, merchantNo:{}", accountSettleCreateReq.getMerchantNo());
    }
}
