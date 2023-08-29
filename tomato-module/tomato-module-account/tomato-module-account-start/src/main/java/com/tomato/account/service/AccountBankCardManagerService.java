package com.tomato.account.service;

import com.tomato.account.dao.AccountBankCardDao;
import com.tomato.account.domain.entity.AccountBankCardEntity;
import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.vo.req.AccountBankCardCreateReq;
import com.tomato.common.enums.YesNoTypeEnum;
import com.tomato.web.core.util.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * 目标银行卡(结算到银行卡使用)管理
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
public class AccountBankCardManagerService {

	private final AccountBankCardDao accountBankCardDao;

	public AccountBankCardManagerService(AccountBankCardDao accountBankCardDao) {
		this.accountBankCardDao = accountBankCardDao;
	}

	public void create(AccountBankCardCreateReq accountBankCardCreateReq, AccountInfoEntity accountInfo) {
		AccountBankCardEntity accountBankCardEntity = BeanUtil.copy(accountBankCardCreateReq,
				AccountBankCardEntity.class);
		accountBankCardEntity.setAccountNo(accountInfo.getAccountNo());
		accountBankCardEntity.setMerchantNo(accountInfo.getMerchantNo());
		accountBankCardEntity.setSettleFlag(YesNoTypeEnum.YES.getValue());
		// TODO 存储加密
		accountBankCardEntity.setCardNo(accountBankCardCreateReq.getCardNo());
		accountBankCardDao.insert(accountBankCardEntity);
	}

}
