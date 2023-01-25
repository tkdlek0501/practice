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
		
		Product product = request.getProductRequest().toEntity(request.getProductRequest());
		OptionGroup optionGroup = request.getOptionGroupRequest().toEntity(request.getOptionGroupRequest());
		Option option = request.getOptionRequest().toEntity(request.getOptionRequest());
		
		// TODO: 매장, 카테고리 생성 선행
		// TODO: optionGroup, option을 list로 받아올 수 있게
		optionGroup.addOption(option);
		product.addOptionGroup(optionGroup);
		
		productRepository.save(product);
	}
	
}
