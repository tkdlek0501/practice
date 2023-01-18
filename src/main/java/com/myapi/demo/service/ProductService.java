package com.myapi.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.Product;
import com.myapi.demo.repository.ProductRepository;
import com.myapi.demo.request.ProductRequest;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {
	
	private final ProductRepository productRepository;
	
	@Transactional
	public void create(ProductRequest request) {
		
		log.info("ProductRequest : {}", request);
		
		Product product = request.toEntity(request);
		productRepository.save(product);
		
		// TODO: test 작성
	}
	
}
