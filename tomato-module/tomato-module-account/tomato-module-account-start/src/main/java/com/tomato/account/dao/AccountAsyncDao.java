package com.tomato.account.dao;

import com.tomato.account.domain.entity.AccountAsyncEntity;
import com.tomato.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 异步入账账户
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Mapper
public interface AccountAsyncDao extends BaseMapper<AccountAsyncEntity, Long> {

	/**
	 * 查询
	 * @return List<AccountAsyncEntity> 账户信息
	 */
	List<AccountAsyncEntity> select();

}
