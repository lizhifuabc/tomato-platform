package com.tomato.gateway.config;

import com.tomato.gateway.handler.CustomAuthenticationEntryPoint;
import com.tomato.gateway.handler.CustomServerAccessDeniedHandler;
import com.tomato.gateway.manager.CustomAuthorizationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 忽略url配置
 *
 * @author lizhifu
 * @since 2023/4/5
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity // 开启方法级别的权限认证
public class WebFluxSecurityConfig {
    private final IgnoreUrlsConfiguration.IgnoreUrlsConfig ignoreUrlsConfig;

    public WebFluxSecurityConfig(IgnoreUrlsConfiguration.IgnoreUrlsConfig ignoreUrlsConfig) {
        this.ignoreUrlsConfig = ignoreUrlsConfig;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        // jwt处理
        httpSecurity.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());;
        // 自定义处理token请求头过期或签名错误的结果
        httpSecurity.oauth2ResourceServer().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        // 自定义处理token请求头鉴权失败的结果
        httpSecurity.oauth2ResourceServer().accessDeniedHandler(new CustomServerAccessDeniedHandler());
        //AJAX进行跨域请求时的预检,需要向另外一个域名的资源发送一个HTTP OPTIONS请求头,用以判断实际发送的请求是否安全
        httpSecurity.authorizeExchange().pathMatchers(HttpMethod.OPTIONS).permitAll();
        //白名单不拦截
        httpSecurity.authorizeExchange().pathMatchers(ignoreUrlsConfig.getUrls()).permitAll();
        /* 请求拦截处理 */
        httpSecurity.authorizeExchange().anyExchange().access(new CustomAuthorizationManager());
        //跨域保护禁用
        httpSecurity.csrf().disable();
        return httpSecurity.build();
    }

    /**
     * 从 JWT 的 scope 中获取的权限 取消 SCOPE_ 的前缀
     * 设置从 jwt claim 中那个字段获取权限
     * 如果需要同多个字段中获取权限或者是通过url请求获取的权限，则需要自己提供jwtAuthenticationConverter()这个方法的实现
     */
    private Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 去掉 SCOPE_ 的前缀
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        // 从jwt claim 中那个字段获取权限，模式是从 scope 或 scp 字段中获取
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("scope");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
