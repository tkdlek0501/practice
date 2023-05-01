package com.myapi.demo.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.domain.User;
import com.myapi.demo.request.MainCategoryRequest;
import com.myapi.demo.service.MainCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1.0/main-category")
public class MainCategoryController {
	
	private final MainCategoryService mainCategoryService;
	
	@PostMapping("")
	public ResponseEntity<Void> create(@Valid @RequestBody MainCategoryRequest request, 
			@AuthenticationPrincipal User user){
		
		mainCategoryService.create(request, user);
		return ResponseEntity.ok(null);
		
	}
	
}
