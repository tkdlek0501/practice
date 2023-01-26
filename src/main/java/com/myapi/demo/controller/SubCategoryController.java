package com.myapi.demo.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.request.SubCategoryRequest;
import com.myapi.demo.service.SubCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1.0/sub-category")
public class SubCategoryController {
	
	private final SubCategoryService subCategoryService;
	
	@PostMapping("")
	public ResponseEntity<Void> create(@Valid @RequestBody SubCategoryRequest request) {
		
		subCategoryService.create(request);
		return ResponseEntity.ok(null);
	}
	
}
