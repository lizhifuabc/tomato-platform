package com.tomato.sys;

import com.tomato.sys.user.dao.SysUserDao;
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
public class SysUserDaoTest {
    @Resource
    SysUserDao sysUserDao;

    @Test
    public void test(){
        sysUserDao.selectById(100L);
        System.out.println("existByLoginName:" + sysUserDao.existByLoginName("123"));
        System.out.println("existByPhone：" + sysUserDao.existByPhone("12312"));
    }
}
