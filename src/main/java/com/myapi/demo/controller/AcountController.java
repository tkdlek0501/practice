package com.myapi.demo.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.exception.BindingException;
import com.myapi.demo.request.CreateUserRequest;
import com.myapi.demo.request.LoginRequest;
import com.myapi.demo.security.JwtTokenProvider;
import com.myapi.demo.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0/acount")
@Slf4j
public class AcountController {
	
	private final UserService userService;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	private final AuthenticationManager authenticationManager;
	
	@GetMapping("")
	public ResponseEntity<Void> test(){
		log.info("test 입니다.");
		return ResponseEntity.ok(null);
	}
	
	// TODO: swaggger 도입
//	@ApiImplication(value = "회원가입")
	@PostMapping("/join")
	public ResponseEntity<Void> join(
			@Validated @RequestBody CreateUserRequest request,
			BindingResult bingdingResult
			){
		
		// TODO: 이런(if문과 for문 조합) 로직 만들지 않기
		if(bingdingResult.hasErrors()) {
			String errorFields = "";
			for(FieldError error : bingdingResult.getFieldErrors()) {
				errorFields += "," + error.getField();
			}
			errorFields.substring(1);
			throw new BindingException(errorFields);
		}
		
		userService.join(request);
		
		return ResponseEntity.ok(null);
	}
	
	// login
	@PostMapping("/doLogin")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        log.info("login : {}", request);
        
        // 이게 provider의 authenticate를 호출?
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        // authenticate 호출 시 비밀번호 검증 및 userDetailService 의 loadUserByUsername 실행
        
        // 권한 확인
        log.info("auth : {}", authentication.getAuthorities()); 
//      if (((UserPrincipal) authentication.getPrincipal()).getType()
//      .equals(UserType.)) {
//      throw new AccessDeniedException("로그인 할 수 없는 유저입니다.");
//  }
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // SecurityContextHolder에 loadUserByUsername의 결과(authentication)를 저장

        String jwt = jwtTokenProvider.createToken(authentication);

        return ResponseEntity.ok(jwt);
    }
	
}