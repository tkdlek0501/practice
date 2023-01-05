package com.myapi.demo.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
		String returnUrl = "/login"; // 로그인 실패시 이동시킬 페이지
		
		String errorMessage = "";
		// provider 에서 회원 조회 안됐을 때 던져준 예외 처리
		if(exception instanceof UsernameNotFoundException) {
			errorMessage = "입력하신 정보로 조회되는 아이디가 없습니다.";
		} else if(exception instanceof BadCredentialsException) {
			errorMessage = exception.getMessage();
		}
		
		response.setStatus(409);
		response.getWriter().print(errorMessage);
		response.getWriter().flush();
	}
}
