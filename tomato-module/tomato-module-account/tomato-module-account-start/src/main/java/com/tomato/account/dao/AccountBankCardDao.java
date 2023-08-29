package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountBankCardEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 目标银行卡(结算到银行卡使用)
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Mapper
public interface AccountBankCardDao {

	/**
	 * 插入
	 * @param accountBankCardEntity 目标银行卡
	 */
	public void insert(AccountBankCardEntity accountBankCardEntity);

}
