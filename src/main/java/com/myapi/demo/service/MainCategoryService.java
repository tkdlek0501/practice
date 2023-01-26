package com.myapi.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.MainCategory;
import com.myapi.demo.repository.MainCategoryRepository;
import com.myapi.demo.request.MainCategoryRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class MainCategoryService {
	
	private final MainCategoryRepository mainCategoryRepository;
	
	@Transactional
	public void create(MainCategoryRequest request) {
		
		MainCategory mainCategory = request.toEntity(request);
		mainCategoryRepository.save(mainCategory);
		
	}
	
}
