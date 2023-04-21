package com.tomato.account.manager;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.dao.AccountInfoDao;
import com.tomato.account.dao.AccountManageHisDao;
import com.tomato.account.dao.AccountRateDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountManageHisEntity;
import com.tomato.account.domain.entity.AccountRateEntity;
import com.tomato.account.domain.req.AccountRateReqList;
import com.tomato.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 账户费率
 *
 * @author lizhifu
 * @since 2023/4/18
 */
@Service
public class AccountRateManager {
    private final AccountRateDao accountRateDao;
    private final AccountInfoDao accountInfoDao;
    private final AccountManageHisDao accountManageHisDao;
    public AccountRateManager(AccountRateDao accountRateDao, AccountInfoDao accountInfoDao, AccountManageHisDao accountManageHisDao) {
        this.accountRateDao = accountRateDao;
        this.accountInfoDao = accountInfoDao;
        this.accountManageHisDao = accountManageHisDao;
    }

    public Optional<AccountRateEntity> getRate(String accountNo, String rateType) {
        return Optional.ofNullable(accountRateDao.selectByAccountNo(accountNo, rateType));
    }

    @Transactional(rollbackFor = Exception.class)
    public void init(AccountRateReqList accountRateReqList) {
        AccountInfoEntity accountInfoEntity = accountInfoDao.selectByAccountNo(accountRateReqList.getAccountNo());
        if (accountInfoEntity == null) {
            throw new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST);
        }
        // 删除
        accountRateDao.deleteByAccountNo(accountRateReqList.getAccountNo());
        // 新增
        StringBuilder sb = new StringBuilder();
        List<AccountRateEntity> list = new ArrayList<>();
        accountRateReqList.getAccountRateBaseReqList().forEach(accountRateReq -> {
            AccountRateEntity accountRateEntity = new AccountRateEntity();
            accountRateEntity.setAccountNo(accountRateReqList.getAccountNo());
            accountRateEntity.setRateType(accountRateReq.getRateType());
            accountRateEntity.setRate(accountRateReq.getRate());
            accountRateEntity.setMerchantNo(accountInfoEntity.getMerchantNo());
            sb.append(accountRateReq.getRateType()).append(":").append(accountRateReq.getRate()).append(";");
            list.add(accountRateEntity);
        });
        // 创建账户管理记录
        AccountManageHisEntity accountManageHisEntity = new AccountManageHisEntity();
        accountManageHisEntity.setAccountNo(accountInfoEntity.getAccountNo());
        // TODO 账户信息回填记录编号
        accountManageHisEntity.setAccountManageSerial(accountInfoEntity.getAccountManageSerial() + 1);
        accountManageHisEntity.setBeforeValue("rate:init");
        accountManageHisEntity.setAfterValue("rate:" + sb);
        accountManageHisDao.insert(accountManageHisEntity);
        accountRateDao.batchInsert(list);
    }
}
