package com.tomato.demo.service.user.service;

import com.tomato.demo.infrastructure.user.mapper.UserEntityMapper;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author lizhifu
 * @since 2023/4/3
 */
@Service
public class UserService {
    private final UserEntityMapper userEntityMapper;

    public UserService(UserEntityMapper userEntityMapper) {
        this.userEntityMapper = userEntityMapper;
    }
}
