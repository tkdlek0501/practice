package com.myapi.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.Option;
import com.myapi.demo.domain.OptionGroup;
import com.myapi.demo.domain.Product;
import com.myapi.demo.repository.ProductRepository;
import com.myapi.demo.request.ProductCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {
	
	private final ProductRepository productRepository;
	
	@Transactional
	public void create(ProductCreateRequest request) {
		
		log.info("ProductRequest : {}", request);
		
		// TODO: test 코드대로 반영하기
		
//		productRepository.save(product);
	}
	
}
