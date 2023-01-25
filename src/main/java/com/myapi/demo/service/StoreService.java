package com.myapi.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.Store;
import com.myapi.demo.repository.StoreRepository;
import com.myapi.demo.request.StoreRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
	
	private final StoreRepository storeRepository;
	
	public void create(StoreRequest storeRequest) {
		
		Store store = storeRequest.toEntity(storeRequest);
		storeRepository.save(store);
	}
	
}
