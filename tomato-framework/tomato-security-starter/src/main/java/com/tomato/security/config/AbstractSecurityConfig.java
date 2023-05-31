package com.tomato.security.config;

import com.tomato.security.handler.CustomAuthenticationHandler;
import com.tomato.security.filter.CustomLoginFilter;
import com.tomato.security.handler.CustomRememberMeServices;
import com.tomato.security.token.TokenService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.function.BiFunction;

/**
 * Spring Security 配置
 *
 * @author lizhifu
 * @since 2022/12/16
 */
public abstract class AbstractSecurityConfig {
    /**
     * Token获取用户信息
     *
     * @return UserDetails
     */
    protected abstract BiFunction<String, HttpServletRequest, UserDetails> userFunction();
    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private TokenService tokenService;
    /**
     * 配置 URL 的安全配置
     * <p>
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                              CustomLoginFilter customLoginFilter,
                                              CustomRememberMeServices customRememberMeServices,
                                              CustomAuthenticationHandler customAuthenticationHandler) throws Exception {
        // 配置
        httpSecurity
                // 禁用basic明文验证
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用 csrf 保护
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用默认登录页
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用默认登出页
                .logout(AbstractHttpConfigurer::disable)
                // 设置异常的EntryPoint，如果不设置，默认使用Http403ForbiddenEntryPoint
                .exceptionHandling(exceptions->exceptions
                        .authenticationEntryPoint(customAuthenticationHandler)
                        .accessDeniedHandler(customAuthenticationHandler)
                )
                // 设置记住我服务
                .rememberMe(rememberMe->rememberMe
                        .rememberMeServices(customRememberMeServices)
                )
                // 基于 token 机制，所以不需要 Session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests->authorizeHttpRequests
                        // 允许所有OPTIONS请求
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // 允许 SpringMVC 的默认错误地址匿名访问
                        .requestMatchers("/error").permitAll()
                        // 免登录的 URL 列表,忽略的url
                        .requestMatchers(securityProperties.getPermitAllUrls().toArray(new String[0])).permitAll()
                        // 允许任意请求被已登录用户访问，不检查Authority
                        .anyRequest().authenticated())
                // 自定义的过滤器，替代UsernamePasswordAuthenticationFilter
                .addFilterBefore(customLoginFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    /**
     * 允许抛出用户不存在的异常
     * @param userDetailsService userDetailsService
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, UserDetailsPasswordService userDetailsPasswordService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setUserDetailsPasswordService(userDetailsPasswordService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }
    /**
     * 自定义登录过滤器
     * @param authenticationManager 认证管理器
     * @param authenticationHandler 认证处理器
     * @param rememberMeServices 记住我服务
     * @return CustomLoginFilter
     */
    @Bean
    public CustomLoginFilter customLoginFilter(AuthenticationManager authenticationManager,
                                               CustomAuthenticationHandler authenticationHandler,
                                               CustomRememberMeServices rememberMeServices,
                                               SecurityProperties securityProperties
                                               ){
        return new CustomLoginFilter(authenticationManager, authenticationHandler, rememberMeServices,securityProperties);
    }
    /**
     * 自定义认证处理器
     * @return CustomAuthenticationHandler
     */
    @Bean
    public CustomAuthenticationHandler customAuthenticationHandler(){
        return new CustomAuthenticationHandler();
    }
    /**
     * 自定义记住我服务
     * @return CustomRememberMeServices
     */
    @Bean
    public CustomRememberMeServices customRememberMeServices(UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository){
        return new CustomRememberMeServices(userDetailsService, tokenRepository);
    }
    /**
     * 自定义RememberMe服务token持久化仓库
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource datasource) {
        final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(datasource);
        //第一次启动的时候建表
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}