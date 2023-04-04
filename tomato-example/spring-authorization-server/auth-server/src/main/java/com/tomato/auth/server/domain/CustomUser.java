package com.tomato.auth.server.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.Map;

/**
 * oauth2用户
 *
 * @author lizhifu
 * @since 2023/4/4
 */
public class CustomUser extends User implements OAuth2AuthenticatedPrincipal {
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    @Override
    public Map<String, Object> getAttributes() {
        // TODO
        return null;
    }

    @Override
    public String getName() {
        // TODO
        return null;
    }
}
