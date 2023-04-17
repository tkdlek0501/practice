package com.myapi.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

import com.myapi.demo.service.security.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig{
	
	private final CustomAuthenticationProvider provider;
	
	private final CustomAuthenticationSuccessHandler successHandler;
	
	private final CustomAuthenticationFailureHandler failureHandler;
	
	private final UserService userService;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationProvider authenticationProvider;
	
	
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
//			.formLogin() 
//				.loginPage("/login") // login이 필요한 페이지 접근시 이동되는 url
//				.loginProcessingUrl("/doLogin") 
//				.successHandler(successHandler)
//				.failureHandler(failureHandler)
//			.and()
				.logout() // logout 활성화
				.logoutUrl("/doLogout") // logout을 실행할 url
				.logoutSuccessUrl("/") // logout 시 이동되는 url
			.and().build();
		
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
	}
	
	// 시큐리티 설정에 굳이 포함 안해도 되는 매핑 경로 (css 등)
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers(
				"/swagger-resources"
	            , "/swagger-resources/configuration/ui"
	            , "/swagger-resources/configuration/security"
	            , "/swagger-ui.html"
	            , "/swagger-ui/"
		)
		.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		//web.ignoring().antMatchers("/static/**", "/assets/**");
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
	}
	
//	 passwordEncoder는 순환참조 이슈 때문에 WebConfig에서 bean 등록
//	TODO: 왜 순환참조 되는지 알아보기
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	// TODO: bean 등록 필요한데.. 순환참조 문제 있음
//	  @Bean
//	  public AuthenticationManager authenticationManagerBean() {
//	    List<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//	    authenticationProviderList.add(authenticationProvider);
//	    ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//	    authenticationManager.setAuthenticationEventPublisher(defaultAuthenticationEventPublisher());
//	    return authenticationManager;
//	  }
//	  
//	  @Bean
//	  DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
//	    return new DefaultAuthenticationEventPublisher();
//	  }
	
}
