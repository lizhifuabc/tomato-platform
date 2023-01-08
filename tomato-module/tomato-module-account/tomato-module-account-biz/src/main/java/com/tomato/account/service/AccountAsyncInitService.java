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
    private static final ConcurrentHashMap<String,AccountAsyncEntity> map = new ConcurrentHashMap();
    private final AccountAsyncDao accountAsyncDao;

    public AccountAsyncInitService(AccountAsyncDao accountAsyncDao) {
        this.accountAsyncDao = accountAsyncDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<AccountAsyncEntity> select = accountAsyncDao.select();
        select.forEach(accountAsyncEntity->{
            map.putIfAbsent(accountAsyncEntity.getAccountNo(),accountAsyncEntity);
        });
    }

    public List<String> accountList(){
        return map.keySet().stream().toList();
    }
    public boolean check(String accountNo){
        return map.containsKey(accountNo);
    }
}
