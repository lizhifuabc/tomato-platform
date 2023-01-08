package com.tomato.account.service;

import com.tomato.account.dao.AccountSettleDao;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.domain.req.AccountSettleCreateReq;
import com.tomato.web.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
@Slf4j
public class AccountSettleManagerService {
    private final AccountSettleDao accountSettleDao;

    public AccountSettleManagerService(AccountSettleDao accountSettleDao) {
        this.accountSettleDao = accountSettleDao;
    }

    /**
     * 创建账户结算规则
     * @param accountSettleCreateReq 账户结算规则
     */
    public void create(AccountSettleCreateReq accountSettleCreateReq, AccountInfoEntity accountInfo){
        AccountSettleEntity accountSettleEntity = BeanUtil.copy(accountSettleCreateReq,AccountSettleEntity.class);
        accountSettleEntity.setAccountNo(accountInfo.getAccountNo());
        accountSettleEntity.setMerchantNo(accountInfo.getMerchantNo());
        // 保存结算规则
        accountSettleDao.insert(accountSettleEntity);
        log.info("设置结算规则, merchantNo:{}, id:{}", accountSettleEntity.getMerchantNo(),accountSettleEntity.getId());
        // 初始化结算控制 TODO
    }
}
