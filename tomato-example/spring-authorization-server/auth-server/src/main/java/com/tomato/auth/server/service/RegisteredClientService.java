package com.tomato.auth.server.service;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;

/**
 * 注册客户端服务
 *
 * @author lizhifu
 * @since 2023/4/5
 */
public interface RegisteredClientService {
    /**
     * 根据客户端id查询
     * @param clientId 客户端id
     * @return RegisteredClient 注册客户端
     */
    Optional<RegisteredClient> findByClientId(String clientId);
}
