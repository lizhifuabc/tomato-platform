package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountSettleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 账户结算规则
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Mapper
public interface AccountSettleDao {

	/**
	 * 插入
	 * @param accountSettleEntity 账户结算规则
	 */
	void insert(AccountSettleEntity accountSettleEntity);

	/**
	 * 查询
	 * @param accountNo accountNo
	 * @return 实体
	 */
	AccountSettleEntity selectByAccountNo(@Param("accountNo") String accountNo);

	/**
	 * 更新
	 * @param accountSettleEntity 账户结算规则
	 */
	int updateByAccountNo(AccountSettleEntity accountSettleEntity);

}
