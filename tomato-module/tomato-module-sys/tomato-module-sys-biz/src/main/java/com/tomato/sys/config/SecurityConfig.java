//package com.tomato.sys.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
///**
// * SpringSecurity 配置类
// *
// * @author lizhifu
// * @date 2022/12/12
// */
//@EnableWebSecurity
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                // CSRF禁用，因为不使用session
//                .csrf().disable()
//                // 认证失败处理类
//                .exceptionHandling().authenticationEntryPoint(new SecurityAuthenticationFailHandler()).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                // 过滤请求
//                .authorizeRequests()
//                //忽略的url
//                .antMatchers(this.getIgnoreUrlList()).permitAll()
//                // 不需要登陆的url
//                .antMatchers(this.getNoNeedLoginUrl()).permitAll()
//                //需要校验权限的url
//                .antMatchers(getAuthenticatedUrlPatterns()).authenticated();
//
//        // token filter 进行校验
//        httpSecurity.addFilterBefore(new SecurityTokenFilter(this.userFunction()), UsernamePasswordAuthenticationFilter.class);
//        httpSecurity.addFilterBefore(corsFilter, SecurityTokenFilter.class);
//        return httpSecurity.build();
//    }
//}
