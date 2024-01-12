package com.tomato.order.infrastructure.repository.impl;

import com.tomato.account.api.RemoteAccountService;
import com.tomato.account.vo.enums.AccountHisTypeEnum;
import com.tomato.account.vo.enums.AccountTypeEnum;
import com.tomato.account.vo.req.AccountTradReq;
import com.tomato.common.domain.resp.Resp;
import com.tomato.order.domain.constants.OrderStatusEnum;
import com.tomato.order.domain.domain.entity.AccountEntity;
import com.tomato.order.domain.repository.AccountRepository;
import com.tomato.order.infrastructure.mapper.OrderInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 账户仓储
 *
 * @author lizhifu
 * @since 2023/8/10
 */
@Repository
@Slf4j
public class AccountRepositoryImpl implements AccountRepository {

	private final RemoteAccountService remoteAccountService;

	private final OrderInfoMapper orderInfoMapper;

	public AccountRepositoryImpl(RemoteAccountService remoteAccountService, OrderInfoMapper orderInfoMapper) {
		this.remoteAccountService = remoteAccountService;
		this.orderInfoMapper = orderInfoMapper;
	}

	@Override
	public void trad(AccountEntity accountEntity) {
		AccountTradReq accountTradReq = AccountTradReq.builder()
			.merchantNo(accountEntity.getMerchantNo())
			.amount(accountEntity.getAmount())
			.sysNo(accountEntity.getSysNo())
			.merchantOrderNo(accountEntity.getMerchantOrderNo())
			.accountType(AccountTypeEnum.SETTLEMENT.getValue())
			.accountHisType(AccountHisTypeEnum.TRAD.getValue())
			.build();
		Resp<Void> trad = remoteAccountService.trad(accountTradReq);
		if (!trad.isSuccess()) {
			// TODO 重试或者记录日志
			log.error("账户入账失败:{},流水号:{}", trad.getMsg(), accountEntity.getSysNo());
			orderInfoMapper.updateAccountStatus(accountEntity.getSysNo(), OrderStatusEnum.FAIL.getValue());
			return;
		}
		orderInfoMapper.updateAccountStatus(accountEntity.getSysNo(), OrderStatusEnum.SUCCESS.getValue());
	}

}
