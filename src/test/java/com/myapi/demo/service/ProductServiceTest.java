package com.myapi.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;
import com.myapi.demo.repository.ProductRepository;
import com.myapi.demo.request.ProductRequest;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class ProductServiceTest {
	
	@Autowired ProductRepository productRepository;
	
	// 상품 생성
	@Test
	public void create() {
		// given
		ProductRequest productRequest = ProductRequest.builder()
				.name("상품1")
				.price(1000)
				.code("M0001")
				.quantity(100)
				.isSoldOut(false)
				.priceControlType(PriceControlType.MAINMALL)
				.build();
		
		// when
		Product product = productRequest.toEntity(productRequest);
		Product createdProduct = productRepository.save(product);
		
		// then
		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
		
		assertEquals(createdProduct, findProduct);
	}
}
