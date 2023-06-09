package com.tomato.sys;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * bcrypt
 *
 * @author lizhifu
 * @since 2023/6/9
 */
@SpringBootTest
public class PasswordTest {
    @Resource
    PasswordEncoder passwordEncoder;
    @Test
    public void test(){
        String admin = passwordEncoder.encode("admin");
        System.out.println(admin);
    }
}
