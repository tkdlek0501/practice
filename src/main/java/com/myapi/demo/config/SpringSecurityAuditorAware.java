package com.myapi.demo.config;

import java.util.Collection;
import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.myapi.demo.domain.User;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableJpaAuditing
@Slf4j
public class SpringSecurityAuditorAware implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		/**
         * SecurityContext 에서 인증정보를 가져와 주입시킨다.
         */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication || !authentication.isAuthenticated()) {
            return Optional.ofNullable("테스트"); // TODO: 테스트 -> null 로 바꾸기
        }else {
        	User user = (User) authentication.getPrincipal();
        	return Optional.ofNullable(user.getNickname());
        }
	}
	
}
