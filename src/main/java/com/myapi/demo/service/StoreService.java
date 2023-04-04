package com.myapi.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.Store;
import com.myapi.demo.domain.User;
import com.myapi.demo.exception.NotFoundUserException;
import com.myapi.demo.repository.StoreRepository;
import com.myapi.demo.repository.UserRepository;
import com.myapi.demo.request.StoreRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
	
	private final StoreRepository storeRepository;
	
	private final UserRepository userRepository;
	
	public void create(StoreRequest storeRequest) {
		
		User user = userRepository.findById(storeRequest.getUserId()).orElseThrow(() -> new NotFoundUserException(""));
		
		Store store = storeRequest.toEntity(storeRequest, user);
		storeRepository.save(store);
	}
	
}
