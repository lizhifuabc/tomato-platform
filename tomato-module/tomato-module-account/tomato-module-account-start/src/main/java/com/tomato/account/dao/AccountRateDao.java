package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountRateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账户费率
 *
 * @author lizhifu
 * @since 2023/4/18
 */
@Mapper
public interface AccountRateDao {

	/**
	 * 根据账户编号查询
	 * @param accountNo 账户编号
	 * @param rateType 费率类型
	 * @return AccountRateEntity 账户费率
	 */
	AccountRateEntity selectByAccountNo(@Param("accountNo") String accountNo, @Param("rateType") String rateType);

	/**
	 * 查询所有
	 * @param accountNo 账户编号
	 * @return AccountRateEntity 账户费率
	 */
	List<AccountRateEntity> selectByAccountNoList(@Param("accountNo") String accountNo);

	/**
	 * 删除
	 * @param accountNo 账户编号
	 */
	void deleteByAccountNo(@Param("accountNo") String accountNo);

	/**
	 * 批量新增
	 * @param list 账户费率
	 */
	void batchInsert(List<AccountRateEntity> list);

}
