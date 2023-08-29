package com.tomato.auth.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/webjars/**");
	}

	// @formatter:off
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize ->
				authorize.anyRequest().authenticated()
			)
			.oauth2Login(oauth2Login ->
				oauth2Login.loginPage("/oauth2/authorization/messaging-client-oidc"))
			.oauth2Client(withDefaults())
			.logout(logout ->
				logout.logoutSuccessHandler(oidcLogoutSuccessHandler()));
		return http.build();
	}
	// @formatter:on

	private LogoutSuccessHandler oidcLogoutSuccessHandler() {
		OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(
				this.clientRegistrationRepository);

		// Set the location that the End-User's User Agent will be redirected to
		// after the logout has been performed at the Provider
		oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/index");

		return oidcLogoutSuccessHandler;
	}

}
