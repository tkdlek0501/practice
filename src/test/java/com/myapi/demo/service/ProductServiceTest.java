package com.myapi.demo.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.Option;
import com.myapi.demo.domain.OptionGroup;
import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;
import com.myapi.demo.repository.ProductRepository;
import com.myapi.demo.request.OptionGroupRequest;
import com.myapi.demo.request.OptionRequest;
import com.myapi.demo.request.ProductRequest;

import lombok.extern.slf4j.Slf4j;

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
		
		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
				.name("옵션그룹1")
				.build();
		
		OptionRequest optionRequest = OptionRequest.builder()
				.name("옵션1")
				.build();
		
		// when
		Product product = productRequest.toEntity(productRequest);
		OptionGroup optionGroup = optionGroupRequest.toEntity(optionGroupRequest);
		Option option = optionRequest.toEntity(optionRequest);
		optionGroup.addOption(option);
		product.addOptionGroup(optionGroup);
		
		Product createdProduct = productRepository.save(product);
		
		// then
		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
		
		
		assertEquals(createdProduct, findProduct);
		assertEquals(createdProduct.getOptionGroups().get(0), findProduct.getOptionGroups().get(0));
		log.info("option : {}", findProduct.getOptionGroups().get(0).getOptions().get(0));
		assertEquals(createdProduct.getOptionGroups().get(0).getOptions().get(0), findProduct.getOptionGroups().get(0).getOptions().get(0));
	}
}
