package com.tomato.account.dao;

import com.tomato.account.domain.bo.*;
import com.tomato.account.domain.entity.AccountDailyCollectEntity;
import com.tomato.account.domain.entity.AccountHisEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 账户历史
 *
 * @author lizhifu
 * @since 2022/12/31
 */
@Mapper
public interface AccountHisDao {

	/**
	 * 查询账户历史表
	 * @param merchantNo 商编
	 * @param sysNo 系统流水号（唯一）
	 * @return 账户历史
	 */
	AccountHisEntity selectBySysNo(@Param("merchantNo") String merchantNo, @Param("sysNo") String sysNo);

	/**
	 * 新增账户历史表
	 * @param accountHisEntity 账户历史表
	 * @return 新增账户历史表数量
	 */
	int insert(AccountHisEntity accountHisEntity);

	/**
	 * 批量更新账户历史状态 分库分表，需要增加 accountNo,或者定制 accountHisId 也作为分表字段
	 * @param accountHisUpdateBatchDO 账户历史
	 * @return 更新数量
	 */
	int updateAccountStatusBatch(AccountHisUpdateBatchBO accountHisUpdateBatchDO);

	/**
	 * 查询未入账的账户历史 account_status = 'DEAL'
	 * @param accountHisDealQueryBO 账号
	 * @return 账户历史
	 */
	AccountHisDealBO selectDeal(@Param("accountHisDealQueryBO") AccountHisDealQueryBO accountHisDealQueryBO);

	/**
	 * 金额汇总 根据创建时间进行汇总：不关注账户状态（异步入账的情况）
	 * @param accountNo 账号
	 * @param start 创建时间开始
	 * @param end 创建时间结束
	 * @param accountHisType 账户历史类型
	 * @return 日汇总账户待结算金额
	 */
	AccountHisCollectResBO collect(@Param("accountNo") String accountNo, @Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end, @Param("accountHisType") String accountHisType);

	/**
	 * 金额汇总 根据创建时间进行汇总：不关注账户状态（异步入账的情况）
	 * @param accountNo 账号
	 * @param start 创建时间开始
	 * @param end 创建时间结束
	 * @return 日汇总账户待结算金额
	 */
	List<AccountDailyCollectEntity> collectGroup(@Param("accountNo") String accountNo,
			@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
