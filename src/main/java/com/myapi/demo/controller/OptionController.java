package com.myapi.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.request.OptionRequest;
import com.myapi.demo.service.OptionService;
import com.myapi.demo.service.ProductService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/option")
@Slf4j
public class OptionController {
	
	private final ProductService productService;
	
	private final OptionService optionService;
	
	// 옵션 그룹 생성
	// 반드시 옵션 그룹내 옵션이 1개 이상 존재해야 한다
	@PostMapping("/group/{productId}")
	public ResponseEntity<Void> addOptionGroup(@PathVariable("productId") Long productId, @RequestBody OptionGroupRequest request){
		
		// 1. product 찾기
		// 2. product에 optionGroup 넣어주기
		// 3. option 까지 넣어주기
		optionService.addOptionGroup(productId, request);
		
		return ResponseEntity.ok(null);
		
	}
	
	// 옵션 개별 추가
	@PostMapping("/option/{optionGroupId}")
	public ResponseEntity<Void> addOption(@PathVariable("optionGroupId") Long optrionGroupId, @RequestBody OptionRequest request){
		
		optionService.addOption(optrionGroupId, request);
		
		return ResponseEntity.ok(null);
	}
}
