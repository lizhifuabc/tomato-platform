package com.tomato.account.service;

import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.domain.bo.AccountBalanceBO;
import com.tomato.account.domain.entity.AccountHisEntity;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.req.AccountTradReq;
import com.tomato.account.manager.AccountHisManager;
import com.tomato.account.manager.AccountInfoManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 账户交易
 *
 * @author lizhifu
 * @since 2023/1/7
 */
@Service
@Slf4j
public class AccountTradService {
    private final AccountInfoDao accountInfoDao;
    private final AccountInfoManager accountInfoManager;
    private final AccountHisManager accountHisManager;
    public AccountTradService(AccountInfoDao accountInfoDao, AccountInfoManager accountInfoManager, AccountHisManager accountHisManager) {
        this.accountInfoDao = accountInfoDao;
        this.accountInfoManager = accountInfoManager;
        this.accountHisManager = accountHisManager;
    }
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void deduct(AccountTradReq accountTradReq){
        log.info("账户扣款 start,{}", accountTradReq);
        AccountInfoEntity account = accountInfoDao.selectByMerchantNo(accountTradReq.getMerchantNo(),accountTradReq.getAccountType());
        // 1.检查账户是否存在
        AccountCheckService.checkAccountExist(account);
        // 2.余额校验
        AccountCheckService.checkBalance(account,accountTradReq.getAmount());
        // 3.校验交易金额
        validateAmount(accountTradReq.getAmount());
        // 2.是否可以出款
        AccountCheckService.checkDeduct(account.getAccountStatus());

        // 3.执行账户出款
        AccountBalanceBO accountBalanceBO = new AccountBalanceBO();
        accountBalanceBO.setAccountNo(account.getAccountNo());
        accountBalanceBO.setVersion(account.getVersion());
        accountBalanceBO.setAmount(accountTradReq.getAmount());
        accountInfoManager.deduct(accountBalanceBO,account);
        // 4.创建账户历史
        AccountHisEntity accountHisEntity = accountHisManager.insert(account,accountTradReq);
        log.info("账户扣款 end,{},accountHisEntity:{}",accountTradReq, accountHisEntity);
    }

    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void deductAsync(AccountTradReq accountTradReq){
        log.info("账户扣款 start,{}", accountTradReq);
        AccountInfoEntity account = accountInfoDao.selectByMerchantNo(accountTradReq.getMerchantNo(),accountTradReq.getAccountType());
        // 1.检查账户是否存在
        AccountCheckService.checkAccountExist(account);
        // 2.余额校验
        AccountCheckService.checkBalance(account,accountTradReq.getAmount());
        // 3.校验交易金额
        validateAmount(accountTradReq.getAmount());
        // 2.是否可以出款
        AccountCheckService.checkDeduct(account.getAccountStatus());
        // 4.创建账户历史
        AccountHisEntity accountHisEntity = accountHisManager.insertAsync(account,accountTradReq);
        log.info("账户扣款 end,{},accountHisEntity:{}",accountTradReq, accountHisEntity);
    }
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void add(AccountTradReq accountTradReq){
        log.info("账户入账 start,{}", accountTradReq);
        AccountInfoEntity account = accountInfoDao.selectByMerchantNo(accountTradReq.getMerchantNo(),accountTradReq.getAccountType());
        // 1.检查账户是否存在
        AccountCheckService.checkAccountExist(account);
        // 2.是否可以收款
        AccountCheckService.checkAdd(account.getAccountStatus());
        // 3.校验交易金额
        validateAmount(accountTradReq.getAmount());
        // 3.执行账户入账
        AccountBalanceBO accountBalanceBO = new AccountBalanceBO();
        accountBalanceBO.setAccountNo(account.getAccountNo());
        accountBalanceBO.setVersion(account.getVersion());
        accountBalanceBO.setAmount(accountTradReq.getAmount());
        accountInfoManager.add(accountBalanceBO,account);
        // 4.创建账户历史
        AccountHisEntity accountHisEntity = accountHisManager.insert(account,accountTradReq);
        log.info("账户入账 end,{},accountHisEntity:{}",accountTradReq, accountHisEntity);
    }
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public void addAsync(AccountTradReq accountTradReq){
        log.info("账户入账 start,{}", accountTradReq);
        AccountInfoEntity account = accountInfoDao.selectByMerchantNo(accountTradReq.getMerchantNo(),accountTradReq.getAccountType());
        // 1.检查账户是否存在
        AccountCheckService.checkAccountExist(account);
        // 2.是否可以收款
        AccountCheckService.checkAdd(account.getAccountStatus());
        // 3.校验交易金额
        validateAmount(accountTradReq.getAmount());
        // 4.创建账户历史
        AccountHisEntity accountHisEntity = accountHisManager.insertAsync(account,accountTradReq);
        log.info("账户入账 end,{},accountHisEntity:{}",accountTradReq, accountHisEntity);
    }
    /**
     * 验证交易金额
     * @param amount 交易金额
     */
    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("交易金额错误");
        }
    }
}
