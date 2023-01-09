package com.myapi.demo.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig{
	
	private final CustomAuthenticationProvider provider;
	private final CustomAuthenticationSuccessHandler successHandler;
	private final CustomAuthenticationFailureHandler failureHandler;
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf()
				.disable() //csrf 토큰 비활성화 (테스트 시 걸어두는 게 좋음)
			.authorizeRequests() // HttpServletRequest 을 사용하여 접근제한 설정 가능
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.antMatchers("/v1.0/acount/**").anonymous()
				.antMatchers("/v1.0/admin/**").hasRole("ADMIN")
				.antMatchers("/v1.0/user/**").hasAnyRole("ADMIN", "USER")
				.anyRequest().permitAll() 
			.and()
			.formLogin() 
				.loginPage("/login") // login이 필요한 페이지 접근시 이동되는 url
				.loginProcessingUrl("/doLogin") 
				.successHandler(successHandler)
				.failureHandler(failureHandler)
			.and()
				.logout() // logout 활성화
				.logoutUrl("/doLogout") // logout을 실행할 url
				.logoutSuccessUrl("/") // logout 시 이동되는 url
			.and().build();
		
	}
	
	// passwordEncoder는 순환참조 이슈 때문에 WebConfig에서 bean 등록
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}
	
	// 시큐리티 설정에 굳이 포함 안해도 되는 매핑 경로 (css 등)
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		//web.ignoring().antMatchers("/static/**", "/assets/**");
	}
}
