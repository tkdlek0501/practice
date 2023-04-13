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

import com.myapi.demo.domain.User;
import com.myapi.demo.domain.UserType;
import com.myapi.demo.exception.BindingException;
import com.myapi.demo.repository.UserRepository;
import com.myapi.demo.request.CreateUserRequest;
import com.myapi.demo.request.LoginRequest;
import com.myapi.demo.security.JwtTokenProvider;
import com.myapi.demo.service.security.UserService;

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
	// TODO: CustomAuthenticationProvider 에서의 authenticate 로직(비밀번호 검증 등) 추가
	@PostMapping("/doLogin")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        log.info("login : {}", request);
        
        // CustomAuthenticationProvider에서는 authentication 만 return 해준다
        // jwt를 이용할거니까 이를 통해 jwt 토큰을 발급해야 한다
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.createToken(authentication);

//        if (((UserPrincipal) authentication.getPrincipal()).getType()
//            .equals(UserType.)) {
//            throw new AccessDeniedException("로그인 할 수 없는 유저입니다.");
//        }

        return ResponseEntity.ok(jwt);
    }
	
}