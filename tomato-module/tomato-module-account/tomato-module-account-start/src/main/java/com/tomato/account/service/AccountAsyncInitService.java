package com.tomato.account.service;

import com.tomato.account.dao.AccountAsyncDao;
import com.tomato.account.domain.entity.AccountAsyncEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 账户异步入账服务
 *
 * @author lizhifu
 * @since 2023/1/8
 */
@Service
public class AccountAsyncInitService implements InitializingBean {
    private static final ConcurrentHashMap<String,AccountAsyncEntity> MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String,AccountAsyncEntity> MERCHANT_MAP = new ConcurrentHashMap<>();
    private final AccountAsyncDao accountAsyncDao;

    public AccountAsyncInitService(AccountAsyncDao accountAsyncDao) {
        this.accountAsyncDao = accountAsyncDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO 从数据库加载，动态更新
        List<AccountAsyncEntity> select = accountAsyncDao.select();
        select.forEach(accountAsyncEntity->{
            MAP.putIfAbsent(accountAsyncEntity.getAccountNo(),accountAsyncEntity);
            MERCHANT_MAP.putIfAbsent(accountAsyncEntity.getMerchantNo(),accountAsyncEntity);
        });
    }

    public List<String> accountList(){
        return MAP.keySet().stream().toList();
    }
    public boolean check(String accountNo){
        return MAP.containsKey(accountNo);
    }
    public boolean checkMerchantNo(String merchantNo){
        return MERCHANT_MAP.containsKey(merchantNo);
    }
    public AccountAsyncEntity get(String accountNo){
        return MAP.get(accountNo);
    }
    public AccountAsyncEntity getMerchantNo(String merchantNo){
        return MERCHANT_MAP.get(merchantNo);
    }
    public void put(AccountAsyncEntity accountAsyncEntity){
        MAP.putIfAbsent(accountAsyncEntity.getAccountNo(),accountAsyncEntity);
        MERCHANT_MAP.putIfAbsent(accountAsyncEntity.getMerchantNo(),accountAsyncEntity);
    }
    public void remove(String accountNo){
        MAP.remove(accountNo);
    }
    public void removeMerchantNo(String merchantNo){
        MERCHANT_MAP.remove(merchantNo);
    }
}
