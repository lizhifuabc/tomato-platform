package com.tomato.sys;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordEncoder
 *
 * @author lizhifu
 * @since 2022/12/18
 */
@SpringBootTest
public class PasswordEncoderTest {
    @Resource
    PasswordEncoder passwordEncoder;

    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("123456"));
    }
}
