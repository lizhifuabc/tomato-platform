package com.tomato.auth.server.service;

import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定义 UserDetailService
 *
 * @author lizhifu
 * @since 2023/4/6
 */
public interface CustomUserDetailService extends UserDetailsService, Ordered {
    /**
     * 排序值 默认取最大的
     * @return 排序值
     */
    default int getOrder() {
        return 0;
    }
}
