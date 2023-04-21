package com.tomato.sys;

import com.tomato.security.enums.LoginDeviceEnum;
import com.tomato.security.token.TokenService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TokenService
 *
 * @author lizhifu
 * @since 2022/12/17
 */
@SpringBootTest
public class TokenServiceTest {
    @Resource
    TokenService tokenService;

    @Test
    public void test(){
        String token = tokenService.generateToken(10000L,"李志福", LoginDeviceEnum.PC);
        System.out.println(token);
        System.out.println(tokenService.decryptToken(token));
        System.out.println(tokenService.getUserId(token));
    }
}
