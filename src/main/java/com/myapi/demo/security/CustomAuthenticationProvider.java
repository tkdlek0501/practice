package com.myapi.demo.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.myapi.demo.domain.User;
import com.myapi.demo.service.security.UserService;

import lombok.RequiredArgsConstructor;

//사용자 인증 체크

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserService userService;
	
	// 로그인 진행시 실행 
	// jwt 이용시 사용 x
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		User user = (User) userService.loadUserByUsername(authentication.getName());
		
		String password = authentication.getCredentials().toString();
		
		// 비밀번호 틀리면
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("아이디 또는 비밀번호가 틀렸습니다."); // failure로 던져줌
		}
		
		// 로그인 성공시 
		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}
		
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
}
