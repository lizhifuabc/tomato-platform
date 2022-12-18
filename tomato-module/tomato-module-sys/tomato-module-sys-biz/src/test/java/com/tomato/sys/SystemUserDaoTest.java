package com.tomato.sys;

import com.tomato.sys.user.dao.SystemUserDao;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * SystemUserDao
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@SpringBootTest
public class SystemUserDaoTest {
    @Resource
    SystemUserDao systemUserDao;

    @Test
    public void test(){
        systemUserDao.selectById(100L);
    }
}
