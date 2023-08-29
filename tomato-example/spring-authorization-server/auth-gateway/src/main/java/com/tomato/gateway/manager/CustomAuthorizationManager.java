package com.tomato.gateway.manager;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 请求拦截处理
 *
 * @author lizhifu
 * @since 2023/4/5
 */
public class CustomAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
			AuthorizationContext authorizationContext) {
		URI uri = authorizationContext.getExchange().getRequest().getURI();
		AntPathMatcher matcher = new AntPathMatcher();
		return authentication.filter(Authentication::isAuthenticated)// 判断是否认证成功
			// 获取认证后的全部权限
			.flatMapIterable(Authentication::getAuthorities)
			.map(GrantedAuthority::getAuthority)
			// 如果权限包含则判断为true
			.any(authority -> matcher.match(authority.replaceFirst("SCOPE_", ""), uri.getPath()))
			.map(AuthorizationDecision::new)
			.defaultIfEmpty(new AuthorizationDecision(false));
	}

	@Override
	public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
		return ReactiveAuthorizationManager.super.verify(authentication, object);
	}

}
