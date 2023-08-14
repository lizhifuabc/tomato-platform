package com.tomato.account;

import com.tomato.account.vo.enums.AccountTypeEnum;
import com.tomato.account.vo.enums.CycleTypeEnum;
import com.tomato.account.vo.enums.SettleTargetTypeEnum;
import com.tomato.account.vo.enums.SettleTypeEnum;
import com.tomato.account.vo.req.AccountCreateReq;
import com.tomato.account.vo.req.AccountSettleCreateReq;
import com.tomato.account.vo.resp.AccountCreateResp;
import com.tomato.common.resp.Resp;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * 账户系统流程测试
 *
 * @author lizhifu
 * @since 2023/8/13
 */
public class AccountFlowTest {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        AccountCreateReq accountCreateReq = new AccountCreateReq();
        accountCreateReq.setMerchantNo("10202307240001002"+ new Random().nextInt(1000));
        accountCreateReq.setAccountType(AccountTypeEnum.SETTLEMENT.getValue());
        accountCreateReq.setRemark("测试");

        HttpEntity<AccountCreateReq> requestEntity = new HttpEntity<>(accountCreateReq);
        ParameterizedTypeReference<Resp<AccountCreateResp>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<Resp<AccountCreateResp>> responseEntity = restTemplate.exchange("http://localhost:9080/account/create", HttpMethod.POST, requestEntity, responseType);
        Resp<AccountCreateResp> accountCreateRespResp = responseEntity.getBody();

        System.out.println("账户系统流程测试:创建账户:返回:"+accountCreateRespResp);

        System.out.println("账户系统流程测试:创建结算规则");
        AccountSettleCreateReq accountSettleCreateReq = new AccountSettleCreateReq();
        accountSettleCreateReq.setAccountNo(accountCreateRespResp.getData().getAccountNo());
        accountSettleCreateReq.setSettleType(SettleTypeEnum.AUTO_SETTLEMENT.getValue());
        accountSettleCreateReq.setMaxSettleDays(10);
        accountSettleCreateReq.setSettleTargetType(SettleTargetTypeEnum.BANK_CARD.getValue());
        accountSettleCreateReq.setReserveDays(1);
        accountSettleCreateReq.setCycleType(CycleTypeEnum.WEEK_WORK.getValue());
        accountSettleCreateReq.setCycleData("11,12,14");
        accountSettleCreateReq.setRemark("测试结算规则");

        Resp resp = restTemplate.postForEntity("http://localhost:9080/account/settle/init",accountSettleCreateReq,Resp.class).getBody();
    }
}
