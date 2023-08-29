package com.tomato.account;

import com.tomato.account.dao.AccountAsyncDao;
import com.tomato.account.domain.entity.AccountAsyncEntity;
import com.tomato.account.service.AccountAsyncInitService;
import com.tomato.account.vo.enums.AccountTypeEnum;
import com.tomato.account.vo.enums.CycleTypeEnum;
import com.tomato.account.vo.enums.SettleTargetTypeEnum;
import com.tomato.account.vo.enums.SettleTypeEnum;
import com.tomato.account.vo.req.AccountCreateReq;
import com.tomato.account.vo.req.AccountSettleCreateReq;
import com.tomato.account.vo.req.AccountTradReq;
import com.tomato.account.vo.resp.AccountCreateResp;
import com.tomato.common.resp.Resp;
import com.tomato.utils.base.lang.UUIDGenerator;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 账户系统流程测试
 *
 * @author lizhifu
 * @since 2023/8/13
 */
@SpringBootTest
public class AccountFlowTest {

	@Resource
	private AccountAsyncDao accountAsyncDao;

	@Resource
	private AccountAsyncInitService accountAsyncInitService;

	@Resource
	private RestTemplate restTemplate;

	@Test
	public void flow() {
		for (int i = 0; i < 5; i++) {
			init();
		}
	}

	public void init() {
		AccountCreateReq accountCreateReq = new AccountCreateReq();
		accountCreateReq.setMerchantNo("10202307240001002" + new Random().nextInt(1000));
		accountCreateReq.setAccountType(AccountTypeEnum.SETTLEMENT.getValue());
		accountCreateReq.setRemark("测试");

		HttpEntity<AccountCreateReq> requestEntity = new HttpEntity<>(accountCreateReq);
		ParameterizedTypeReference<Resp<AccountCreateResp>> responseType = new ParameterizedTypeReference<>() {
		};
		ResponseEntity<Resp<AccountCreateResp>> responseEntity = restTemplate
			.exchange("http://localhost:9080/account/create", HttpMethod.POST, requestEntity, responseType);
		Resp<AccountCreateResp> accountCreateRespResp = responseEntity.getBody();

		System.out.println("账户系统流程测试:创建账户:返回:" + accountCreateRespResp);

		System.out.println("账户系统流程测试:创建账户:异步入库");
		AccountAsyncEntity accountAsyncEntity = new AccountAsyncEntity();
		accountAsyncEntity.setAccountNo(accountCreateRespResp.getData().getAccountNo());
		accountAsyncEntity.setMerchantNo(accountCreateReq.getMerchantNo());
		accountAsyncDao.insertSelective(accountAsyncEntity);
		accountAsyncInitService.put(accountAsyncEntity);
		System.out.println("账户系统流程测试:创建账户:异步入库:完成");

		System.out.println("账户系统流程测试:创建结算规则");
		AccountSettleCreateReq accountSettleCreateReq = new AccountSettleCreateReq();
		accountSettleCreateReq.setAccountNo(accountCreateRespResp.getData().getAccountNo());
		accountSettleCreateReq.setSettleType(SettleTypeEnum.AUTO_SETTLEMENT.getValue());
		accountSettleCreateReq.setMaxSettleDays(10);
		accountSettleCreateReq.setSettleTargetType(SettleTargetTypeEnum.BANK_CARD.getValue());
		accountSettleCreateReq.setReserveDays(1);
		accountSettleCreateReq.setCycleType(CycleTypeEnum.WEEK.getValue());
		accountSettleCreateReq.setCycleData("1,2,3,4,5,6,7");
		accountSettleCreateReq.setRemark("测试结算规则");

		Resp resp = restTemplate
			.postForEntity("http://localhost:9080/account/settle/init", accountSettleCreateReq, Resp.class)
			.getBody();
		System.out.println("账户系统流程测试:创建结算规则:返回:" + resp);

		System.out.println("账户系统流程测试:入账");
		BigDecimal sum = new BigDecimal(0);
		for (int i = 0; i < 100; i++) {
			AccountTradReq accountTradReq = new AccountTradReq();
			accountTradReq.setMerchantNo(accountCreateReq.getMerchantNo());
			int amount = new Random().nextInt(1000);
			accountTradReq.setAmount(BigDecimal.valueOf(amount));
			accountTradReq.setSysNo(UUIDGenerator.get32UUID());
			accountTradReq.setMerchantOrderNo(UUIDGenerator.get32UUID());
			accountTradReq.setAccountType(AccountTypeEnum.SETTLEMENT.getValue());
			accountTradReq.setAccountHisType(AccountTypeEnum.SETTLEMENT.getValue());
			accountTradReq.setAsync(true);

			Resp resp1 = restTemplate.postForEntity("http://localhost:9080//account/trad", accountTradReq, Resp.class)
				.getBody();
			System.out.println("账户系统流程测试:入账:返回:" + resp1);
			sum = sum.add(accountTradReq.getAmount());
		}
		System.out.println("账户系统流程测试:入账:总金额:" + sum);
	}

}
