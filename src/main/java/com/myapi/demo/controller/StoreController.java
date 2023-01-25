package com.myapi.demo.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.request.StoreRequest;
import com.myapi.demo.service.StoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1.0/store")
public class StoreController {
	
	private final StoreService storeService;
	
	@PostMapping("")
	public ResponseEntity<Void> create(@Valid @RequestBody StoreRequest request){
		
		storeService.create(request);
		return ResponseEntity.ok(null);
	}
	
	
}
