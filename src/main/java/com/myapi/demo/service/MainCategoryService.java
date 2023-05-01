package com.myapi.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.MainCategory;
import com.myapi.demo.domain.Store;
import com.myapi.demo.domain.User;
import com.myapi.demo.exception.NotFoundStoreException;
import com.myapi.demo.repository.MainCategoryRepository;
import com.myapi.demo.repository.StoreRepository;
import com.myapi.demo.request.MainCategoryRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class MainCategoryService {
	
	private final MainCategoryRepository mainCategoryRepository;
	
	private final StoreRepository storeRepository;
	
	@Transactional
	public void create(MainCategoryRequest request, User user) {
		
		Store store = storeRepository.findByUser(user).orElseThrow(() -> new NotFoundStoreException(null));
		
		MainCategory mainCategory = request.toEntity(request, store);
		mainCategoryRepository.save(mainCategory);
		
	}
	
}
