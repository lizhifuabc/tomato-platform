package com.tomato.account.manager;

import com.tomato.account.domain.req.AccountCreateReq;
import com.tomato.util.StrUtil;
import com.tomato.util.date.DatePattern;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 账户编号生成
 *
 * @author lizhifu
 * @since 2023/1/1
 */
@Service
public class AccountNoManager {
    public String nextStringValue(AccountCreateReq accountCreateReq){
        // TODO 账户编号生成唯一ID 是否使用 Long 类型
        Long aLong = ThreadLocalRandom.current().nextLong(1, 9999999);
        String date = LocalDate.now().format(DatePattern.PURE_DATE_FORMATTER);
        return "10" + date + StrUtil.fillAfter("0",aLong.toString(),7) + accountCreateReq.getMerchantNo().substring(accountCreateReq.getMerchantNo().length()-4);
    }
}
