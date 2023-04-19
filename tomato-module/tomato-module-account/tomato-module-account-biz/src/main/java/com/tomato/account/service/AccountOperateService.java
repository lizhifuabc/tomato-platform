package com.tomato.account.service;

import com.tomato.account.enums.AccountStatusTypeEnum;
import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountManageHisDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountManageHisEntity;
import com.tomato.account.domain.req.AccountCancelledReq;
import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.domain.req.AccountFreezeReq;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.domain.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 *  账户管理操作
 *  <p>1. 账户创建</p>
 *  <p>2. 注销账户</p>
 *  <p>3. 冻结解冻</p>
 * @author lizhifu
 * @since 2023/1/7
 */
@Service
@Slf4j
public class AccountOperateService {
    private final AccountInfoManager accountInfoManager;
    private final AccountInfoDao accountInfoDao;
    private final AccountManageHisDao accountManageHisDao;
    public AccountOperateService(AccountInfoManager accountInfoManager, AccountInfoDao accountInfoDao, AccountManageHisDao accountManageHisDao) {
        this.accountInfoManager = accountInfoManager;
        this.accountInfoDao = accountInfoDao;
        this.accountManageHisDao = accountManageHisDao;
    }

    /**
     * 账户创建
     * @param accountCreateReq 账户创建
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public AccountInfoEntity createAccount(AccountCreateReq accountCreateReq){
        // 1.检查是否已经创建账户(不解决并发问题)
        // 这个地方是检查该商编是否已经存在账户，所以这个地方不能使用通过账户号来查询
        AccountInfoEntity account = accountInfoDao.selectByMerchantNo(accountCreateReq.getMerchantNo(),accountCreateReq.getAccountType());
        AccountCheckService.checkAccountNotExist(account);
        // 2.创建账户
        account = accountInfoManager.create(accountCreateReq);
        // 3.创建账户管理记录
        AccountManageHisEntity accountManageHisEntity = new AccountManageHisEntity();
        accountManageHisEntity.setAccountNo(account.getAccountNo());
        accountManageHisEntity.setAccountManageSerial(0L);
        accountManageHisEntity.setBeforeValue("status:null");
        accountManageHisEntity.setAfterValue("status:"+account.getAccountStatus());
        accountManageHisDao.insert(accountManageHisEntity);
        return account;
    }

    /**
     * 注销账户
     * @param accountCancelledReq 注销账户
     */
    public void cancelledAccount(AccountCancelledReq accountCancelledReq) {
        //1.检查是否已经注销账户
        AccountInfoEntity account = accountInfoDao.selectByAccountNo(accountCancelledReq.getAccountNo());
        AccountCheckService.checkAccountExist(account);
        if(AccountStatusTypeEnum.ACCOUNT_CANCELLED.getValue().equals(account.getAccountStatus())){
            log.info("账户已经注销,直接返回, accountNo:{}", accountCancelledReq.getAccountNo());
            return;
        }
        //2.检查余额是否为0
        if (BigDecimal.ZERO.compareTo(account.getBalance()) != 0) {
            log.info("账号的账户余额不为零, accountNo:{}", accountCancelledReq.getAccountNo());
            throw new BusinessException(AccountRespCode.ACCOUNT_CANCEL_FAIL);
        }
        log.info("开始注销账户，merchantNo:{}, accountNo:{}", account.getMerchantNo(), account.getAccountNo());
        //3.注销账户,账户管理序列号加1
        accountInfoManager.updateAccountStatus(account.getAccountNo(), AccountStatusTypeEnum.ACCOUNT_CANCELLED.getValue() ,account.getVersion());
        // 4.创建账户管理记录
        AccountManageHisEntity accountManageHisEntity = new AccountManageHisEntity();
        accountManageHisEntity.setAccountNo(account.getAccountNo());
        accountManageHisEntity.setAccountManageSerial(account.getAccountManageSerial() + 1);
        accountManageHisEntity.setBeforeValue("status:" + account.getAccountStatus());
        accountManageHisEntity.setAfterValue("status:" + AccountStatusTypeEnum.ACCOUNT_CANCELLED.getValue());
        accountManageHisDao.insert(accountManageHisEntity);
    }

    /**
     * 冻结解冻
     * @param accountFreezeReq 冻结解冻
     */
    public void freezeOrUnfreeze(AccountFreezeReq accountFreezeReq) {
        // 1.检查账户是否存在
        AccountInfoEntity account = accountInfoDao.selectByAccountNo(accountFreezeReq.getAccountNo());
        AccountCheckService.checkAccountExist(account);
        if(AccountStatusTypeEnum.ACCOUNT_CANCELLED.name().equals(account.getAccountStatus())){
            throw new BusinessException(AccountRespCode.ACCOUNT_STATUS_NOT_ACTIVE);
        }
        // 2.冻结或者解冻
        accountInfoManager.updateAccountStatus(account.getAccountNo(), accountFreezeReq.getAccountStatus(),account.getVersion());
        // 3.创建账户管理记录
        AccountManageHisEntity accountManageHisEntity = new AccountManageHisEntity();
        accountManageHisEntity.setAccountNo(account.getAccountNo());
        accountManageHisEntity.setAccountManageSerial(account.getAccountManageSerial() + 1);
        accountManageHisEntity.setBeforeValue("status:" + account.getAccountStatus());
        accountManageHisEntity.setAfterValue("status:" + accountFreezeReq.getAccountStatus());
        accountManageHisDao.insert(accountManageHisEntity);
    }
}
