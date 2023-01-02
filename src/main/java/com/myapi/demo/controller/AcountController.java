package com.myapi.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0/acount")
@Slf4j
public class AcountController {
	
	@GetMapping("")
	public ResponseEntity<Void> test(){
		log.info("test 입니다.");
		return ResponseEntity.ok(null);
	}
	
}
