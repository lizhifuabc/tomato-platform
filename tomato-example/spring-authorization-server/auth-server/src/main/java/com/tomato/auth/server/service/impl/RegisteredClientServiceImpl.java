package com.tomato.auth.server.service.impl;

import com.tomato.auth.server.service.RegisteredClientService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 注册客户端服务
 *
 * @author lizhifu
 * @since 2023/4/5
 */
@Service
public class RegisteredClientServiceImpl implements RegisteredClientService {
    private final RegisteredClientRepository registeredClientRepository;

    public RegisteredClientServiceImpl(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public Optional<RegisteredClient> findByClientId(String clientId) {
        return Optional.ofNullable(registeredClientRepository.findByClientId(clientId));
    }
}
