package com.myapi.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.SubCategory;
import com.myapi.demo.repository.SubCategoryRepository;
import com.myapi.demo.request.SubCategoryRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SubCategoryService {
	
	private final SubCategoryRepository subCategoryRepository;
	
	@Transactional
	public void create(SubCategoryRequest request) {
		
		SubCategory subCategory = request.toEntity(request);
		subCategoryRepository.save(subCategory);
	}
	
}
