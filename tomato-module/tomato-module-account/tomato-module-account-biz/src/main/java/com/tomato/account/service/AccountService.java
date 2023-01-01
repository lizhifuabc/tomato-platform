//package com.tomato.account.service;
//
//import com.tomato.account.dao.AccountDao;
//import com.tomato.account.dao.AccountHisDao;
//import com.tomato.account.database.accountHisDao;
//import com.tomato.account.database.accountDao;
//import com.tomato.account.database.dataobject.*;
//import com.tomato.account.domain.entity.AccountEntity;
//import com.tomato.account.domain.entity.AccountHisEntity;
//import com.tomato.account.enums.AccountStatusEnum;
//import com.tomato.account.exception.AccountException;
//import com.tomato.account.exception.AccountResponseCode;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.Objects;
//
///**
// * 账户操作服务
// *
// * @author lizhifu
// * @date 2022/6/7
// */
//@Service
//@Slf4j
//public class AccountService {
//    private final AccountDao accountDao;
//    private final AccountHisDao accountHisDao;
//
//    public AccountService(AccountDao accountDao, AccountHisDao accountHisDao) {
//        this.accountDao = accountDao;
//        this.accountHisDao = accountHisDao;
//    }
//
//    /**
//     * TODO 批量入账
//     * 账户余额操作，将数据库操作放在服务中，便于后面重试；
//     * 如果没有重试，为了减少事务，可以将查询操作提前
//     * @param accountHisId
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public void exe(Long accountHisId,String accountNo) {
//        AccountHisEntity accountHisEntity = accountHisDao.selectByAccountHisId(accountHisId,accountNo);
//        AccountEntity accountEntity = accountDao.selectByAccountNo(accountHisEntity.getAccountNo());
//        log.info("账户余额操作开始 accountEntity:{},accountHisEntity:{}", accountEntity,accountHisEntity);
//
//        AccountHisUpdateDO accountHisUpdateDO = new AccountHisUpdateDO();
//        accountHisUpdateDO.setAccountNo(AccountHisEntity.getAccountNo());
//        accountHisUpdateDO.setAccountHisId(AccountHisEntity.getAccountHisId());
//        accountHisUpdateDO.setAccountStatus(AccountStatusEnum.SUCCESS.getCode());
//        accountHisUpdateDO.setBeforeBalance(AccountEntity.getBalance());
//        accountHisUpdateDO.setAfterBalance(AccountEntity.getBalance().add(AccountHisEntity.getAmount()));
//        accountHisUpdateDO.setVersion(AccountHisEntity.getVersion());
//        int updateState = accountHisDao.updateAccountStatus(accountHisUpdateDO);
//        log.info("更新账户历史状态 account:{},accountHisId:{},updateState:{}",AccountEntity.getAccountNo(),accountHisId,updateState);
//        if (updateState == 0) {
//            throw new AccountException(AccountResponseCode.ACCOUNT_HIS_UPDATE_FAIL);
//        }
//
//        int accountResult;
//        if (AccountHisEntity.getAmount().compareTo(BigDecimal.ZERO) > 0) {
//            accountResult = accountDao.add(AccountEntity.getAccountNo(), AccountHisEntity.getAmount(), AccountEntity.getVersion());
//        } else {
//            accountResult = accountDao.deduct(AccountEntity.getAccountNo(), AccountHisEntity.getAmount(), AccountEntity.getVersion());
//        }
//        log.info("更新账户余额 account:{},accountHisId:{},accountResult:{}",AccountEntity.getAccountNo(),accountHisId,accountResult);
//        if (accountResult == 0) {
//            throw new AccountException(AccountResponseCode.ACCOUNT_UPDATE_FAIL);
//        }
//    }
//
//    /**
//     * 批量入账:针对add
//     * @param accountNo
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public void exeBatch(String accountNo) {
//        AccountEntity AccountEntity = accountDao.selectByAccountNo(accountNo);
//        // 查询未入账金额和记录
//        AccountHisDealDO accountHisDealDO = accountHisDao.selectDeal(accountNo);
//        if(Objects.isNull(accountHisDealDO) || accountHisDealDO.getAccountHisIds().isBlank()
//                || accountHisDealDO.getSum().compareTo(BigDecimal.ZERO) < 0){
//            return;
//        }
//        AccountHisUpdateBatchDO accountHisUpdateBatchDO = new AccountHisUpdateBatchDO();
//        accountHisUpdateBatchDO.setAccountHisId(Arrays.asList(accountHisDealDO.getAccountHisIds().split(",")));
//        accountHisUpdateBatchDO.setAccountStatus(AccountStatusEnum.SUCCESS.getCode());
//        accountHisUpdateBatchDO.setAccountNo(accountNo);
//        accountHisUpdateBatchDO.setVersion(0);
//        accountHisUpdateBatchDO.setBeforeBalance(AccountEntity.getBalance());
//        accountHisUpdateBatchDO.setAfterBalance(AccountEntity.getBalance().add(accountHisDealDO.getSum()));
//        // 更新账户历史记录
//        int res = accountHisDao.updateAccountStatusBatch(accountHisUpdateBatchDO);
//        // 更新数量判断
//        if(res != accountHisUpdateBatchDO.getAccountHisId().size()){
//            throw new AccountException(AccountResponseCode.ACCOUNT_HIS_UPDATE_FAIL);
//        }
//        // 更新账户余额
//        int addRes = accountDao.add(AccountEntity.getAccountNo(), accountHisDealDO.getSum(),AccountEntity.getVersion());
//        if(addRes != 1){
//            throw new AccountException(AccountResponseCode.ACCOUNT_UPDATE_FAIL);
//        }
//    }
//}
