package com.myapi.demo.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.myapi.demo.domain.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler  implements AuthenticationSuccessHandler{
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		// 회원 객체 가져오기
		User user = (User) authentication.getPrincipal();
		// 회원 권한(등급) 가져오기
		Collection<? extends GrantedAuthority> authority = user.getAuthorities(); 
		
		// 권한에 따른 redirect 
//		String grade = String.valueOf(authority);
//		if(grade.equals("ROLE_ADMIN")) {
//			response.sendRedirect("/admin");
//		}else {
//			response.sendRedirect("/user");
//		}
		
	}
	
	
}
