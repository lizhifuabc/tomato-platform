package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountInfoEntity;
import com.tomato.account.domain.entity.AccountSettleRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * 账户结算记录
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Mapper
public interface AccountSettleRecordDao {

	/**
	 * 插入
	 * @param accountSettleRecordEntity 账户结算记录
	 */
	void insert(AccountSettleRecordEntity accountSettleRecordEntity);

	/**
	 * 根据账号和结算日期查询
	 * @param accountNo 账号
	 * @param settleDate 结算日期
	 * @return 实体
	 */
	AccountSettleRecordEntity selectByAccountNoAndSettleDate(@Param("accountNo") String accountNo,
			@Param("settleDate") LocalDate settleDate);

}
