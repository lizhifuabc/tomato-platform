package com.tomato.auth.server.service;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 注册客户端服务
 *
 * @author lizhifu
 * @since 2023/4/6
 */
@Service
public class RegisteredClientService {
    private final RegisteredClientRepository registeredClientRepository;

    public RegisteredClientService(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    /**
     * 根据客户端id获取注册客户端
     * @param registeredClientId 客户端id
     * @return 注册客户端
     */
    public Optional<RegisteredClient> getRegisteredClientById(String registeredClientId){
        return Optional.ofNullable(registeredClientRepository.findById(registeredClientId));
    }
}
