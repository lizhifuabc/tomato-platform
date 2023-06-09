package com.tomato.sys.domain.service;

/**
 * token
 *
 * @author lizhifu
 * @since 2023/6/9
 */
public interface SysTokenService {
    void saveToken(String username,String token, String refreshToken);
}
