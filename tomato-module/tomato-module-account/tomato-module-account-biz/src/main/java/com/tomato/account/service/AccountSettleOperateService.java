package com.tomato.account.service;

import com.tomato.account.constant.AccountRespCode;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleControlEntity;
import com.tomato.account.domain.entity.AccountSettleEntity;
import com.tomato.account.domain.req.AccountSettleCreateReq;
import com.tomato.account.manager.AccountInfoManager;
import com.tomato.account.manager.AccountSettleControlManager;
import com.tomato.account.manager.AccountSettleManager;
import com.tomato.domain.core.exception.BusinessException;
import com.tomato.web.util.BeanUtil;
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
public class AccountSettleOperateService {
    private final AccountSettleManager accountSettleManager;
    private final AccountInfoManager accountInfoManager;
    private final AccountSettleControlManager accountSettleControlManager;

    public AccountSettleOperateService(AccountSettleManager accountSettleManager, AccountInfoManager accountInfoManager, AccountSettleControlManager accountSettleControlManager) {
        this.accountSettleManager = accountSettleManager;
        this.accountInfoManager = accountInfoManager;
        this.accountSettleControlManager = accountSettleControlManager;
    }

    /**
     * 账号结算控制
     * @param accountSettleCreateReq 账号结算控制
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void create(AccountSettleCreateReq accountSettleCreateReq){
        AccountInfoEntity accountInfoEntity = accountInfoManager.selectByAccountNo(accountSettleCreateReq.getAccountNo()).orElseThrow(()-> new BusinessException(AccountRespCode.ACCOUNT_NOT_EXIST));
        // 创建账户结算基本信息
        AccountSettleEntity accountSettleEntity = BeanUtil.copy(accountSettleCreateReq,AccountSettleEntity.class);
        accountSettleEntity.setAccountNo(accountInfoEntity.getAccountNo());
        accountSettleEntity.setMerchantNo(accountInfoEntity.getMerchantNo());
        accountSettleManager.create(accountSettleEntity);
        // 创建账户结算控制
        accountSettleControlManager.create(accountSettleEntity);
    }

    /**
     * 更新账号结算控制
     * @param accountSettleCreateReq 账号结算控制
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void update(AccountSettleCreateReq accountSettleCreateReq) {
        AccountSettleEntity dao = accountSettleManager.selectByAccountNo(accountSettleCreateReq.getAccountNo()).orElseThrow(() -> new BusinessException("账户结算规则不存在"));
        // 更新账户结算基本信息
        AccountSettleEntity accountSettleEntity = BeanUtil.copy(accountSettleCreateReq,AccountSettleEntity.class);
        accountSettleEntity.setVersion(dao.getVersion());
        accountSettleManager.updateByAccountNo(accountSettleEntity);
        // TODO 自动结算更改为自助结算，是否可以
        // 更新账户结算控制
        AccountSettleControlEntity accountSettleControl = accountSettleControlManager.selectByAccountNo(accountSettleCreateReq.getAccountNo()).orElseThrow(() -> new BusinessException("账户结算规则控制不存在"));
        accountSettleControlManager.updateSettleControl(accountSettleEntity,accountSettleControl);
    }
}
