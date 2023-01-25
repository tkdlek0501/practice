package com.myapi.demo.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.request.ProductCreateRequest;
import com.myapi.demo.request.ProductRequest;
import com.myapi.demo.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0/product")
@Slf4j
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping("")
	public ResponseEntity<Void> create(@Valid @RequestBody ProductCreateRequest request) {
		
		productService.create(request);
		return ResponseEntity.ok(null);
	}
	
	
	
}
