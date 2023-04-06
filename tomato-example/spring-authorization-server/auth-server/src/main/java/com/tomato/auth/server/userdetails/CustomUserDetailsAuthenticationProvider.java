package com.tomato.auth.server.userdetails;

import com.tomato.auth.server.service.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * 自定义用户认证
 *
 * @author lizhifu
 * @since 2023/4/6
 */
@Slf4j
public class CustomUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final Map<String, CustomUserDetailService> userDetailsServiceMap;

    public CustomUserDetailsAuthenticationProvider(Map<String, CustomUserDetailService> userDetailsServiceMap) {
        log.info("CustomUserDetailsAuthenticationProvider init:{}",userDetailsServiceMap);
        this.userDetailsServiceMap = userDetailsServiceMap;
    }

    /**
     * 身份验证
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    /**
     * 授权逻辑
     * @param username
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        log.info("CustomUserDetailsAuthenticationProvider retrieveUser username:{}",username);
        Optional<CustomUserDetailService> optional = userDetailsServiceMap.values().stream()
                .max(Comparator.comparingInt(Ordered::getOrder));
        optional.orElseThrow((() -> new InternalAuthenticationServiceException("UserDetailsService error , not register")));

        UserDetails loadedUser = optional.get().loadUserByUsername(username);
        Optional.ofNullable(loadedUser).orElseThrow((() -> new UsernameNotFoundException("username: [" + username + "] do not exist!")));
        return loadedUser;
    }
}
