package com.myapi.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.domain.User;
import com.myapi.demo.exception.BindingException;
import com.myapi.demo.repository.UserRepository;
import com.myapi.demo.request.CreateUserRequest;
import com.myapi.demo.service.security.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0/acount")
@Slf4j
public class AcountController {
	
	private final UserService userService;
	
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
}