package com.myapi.demo.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.MainCategory;
import com.myapi.demo.domain.Option;
import com.myapi.demo.domain.OptionGroup;
import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;
import com.myapi.demo.domain.ProductLog;
import com.myapi.demo.domain.Store;
import com.myapi.demo.domain.SubCategory;
import com.myapi.demo.domain.User;
import com.myapi.demo.domain.UserType;
import com.myapi.demo.dto.ProductSearchCondition;
import com.myapi.demo.dto.ProductSearchDto;
import com.myapi.demo.dto.TempProduct;
import com.myapi.demo.event.dto.UpdatedProductEvent;
import com.myapi.demo.repository.MainCategoryRepository;
import com.myapi.demo.repository.ProductRepository;
import com.myapi.demo.repository.StoreRepository;
import com.myapi.demo.repository.SubCategoryRepository;
import com.myapi.demo.repository.UserRepository;
import com.myapi.demo.request.OptionGroupRequest;
import com.myapi.demo.request.OptionRequest;
import com.myapi.demo.request.ProductRequest;
import com.myapi.demo.request.ProductUpdateRequest;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class ProductServiceTest {
	
	@Autowired ProductRepository productRepository;
	
	@Autowired StoreRepository storeRepository;
	
	@Autowired MainCategoryRepository mainCategoryRepository;
	
	@Autowired SubCategoryRepository subCategoryRepository;
	
	@Autowired UserRepository userRepository;
	
	@Autowired PasswordEncoder passwordEncoder;
	
	@Autowired ApplicationEventPublisher eventPublisher;
	
	// 상품 생성
	@Test
	public void create() {
		// given
		
		// user
		User user = User.builder()
				.username("이름1")
				.password(passwordEncoder.encode("abcde"))
				.nickname("닉네임1")
				.type(UserType.MAINMALL)
				.build();
		User createdUser = userRepository.save(user);
		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
		
		// store
		Store store = Store.builder()
		.name("매장1")
		.businessNo("12345")
		.description("첫번째 매장입니다.")
		.build();
		store.changeUser(findUser);
		Store createdStore = storeRepository.save(store);
		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
		
		// category
		MainCategory mainCategory = MainCategory.builder()
				.name("메인카테고리1")
				.build();
		mainCategory.changeStore(findStore);
		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
		
		SubCategory subCategory = SubCategory.builder()
				.name("서브카테고리1")
				.build();
		subCategory.changeMainCategory(findMainCategory);
		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
		
		// product
		ProductRequest productRequest = ProductRequest.builder()
				.name("상품1")
				.price(1000)
				.code("M0001")
				.quantity(100)
				.isSoldOut(false)
				.priceControlType(PriceControlType.MAINMALL)
				.build();
		
		OptionRequest optionRequest = OptionRequest.builder()
				.name("옵션1")
				.build();
		
		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
				.name("옵션그룹1")
				.build();
		
		optionGroupRequest.getOptionRequests().add(optionRequest);
		productRequest.getOptionGroupRequests().add(optionGroupRequest);
		
		// when
		Product product = productRequest.toEntity(productRequest);
		
		product.changeStore(findStore);
		product.changeSubCategory(findSubCategory);
		
		List<OptionGroup> optionGroups = productRequest.getOptionGroupRequests().stream()
				.map(ogr -> {
					OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
					List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
					options.forEach(opt -> optionGroup.addOption(opt));
					return optionGroup;
				})
				.collect(Collectors.toList());
		
		optionGroups.forEach(og -> product.addOptionGroup(og));
		
		Product createdProduct = productRepository.save(product);
		
		// then
		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
		
		
		assertEquals(createdProduct, findProduct);
		assertEquals(createdProduct.getOptionGroups().get(0), findProduct.getOptionGroups().get(0));
		log.info("option : {}", findProduct.getOptionGroups().get(0).getOptions().get(0));
		assertEquals(createdProduct.getOptionGroups().get(0).getOptions().get(0), findProduct.getOptionGroups().get(0).getOptions().get(0));
	}
	
	@Test
	public void search() {
		// given
		
		// user
		User user = User.builder()
				.username("이름1")
				.password(passwordEncoder.encode("abcde"))
				.nickname("닉네임1")
				.type(UserType.MAINMALL)
				.build();
		User createdUser = userRepository.save(user);
		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
		
		// store
		Store store = Store.builder()
		.name("매장1")
		.businessNo("12345")
		.description("첫번째 매장입니다.")
		.build();
		store.changeUser(findUser);
		Store createdStore = storeRepository.save(store);
		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
		
		// category
		MainCategory mainCategory = MainCategory.builder()
				.name("메인카테고리1")
				.build();
		mainCategory.changeStore(findStore);
		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
		
		SubCategory subCategory = SubCategory.builder()
				.name("서브카테고리1")
				.build();
		subCategory.changeMainCategory(findMainCategory);
		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
		
		// product
		ProductRequest productRequest = ProductRequest.builder()
				.name("상품1")
				.price(1000)
				.code("M0001")
				.quantity(100)
				.isSoldOut(false)
				.priceControlType(PriceControlType.MAINMALL)
				.build();
		
		OptionRequest optionRequest = OptionRequest.builder()
				.name("옵션1")
				.build();
		
		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
				.name("옵션그룹1")
				.build();
		
		optionGroupRequest.getOptionRequests().add(optionRequest);
		productRequest.getOptionGroupRequests().add(optionGroupRequest);
		
		ProductSearchCondition condition = ProductSearchCondition.builder()
				.priceGoe(500)
				.priceLoe(2000)
				.build();
		
		
		// when
		List<ProductSearchDto> productSearchDto = productRepository.search(condition);
		
		// then
		log.info("productSearchDto : {}", productSearchDto);
	}
	
	@Test
	@Rollback(false)
	public void update() {
		// given
		
		// user
		User user = User.builder()
				.username("이름1")
				.password(passwordEncoder.encode("abcde"))
				.nickname("닉네임1")
				.type(UserType.MAINMALL)
				.build();
		User createdUser = userRepository.save(user);
		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
		
		// store
		Store store = Store.builder()
		.name("매장1")
		.businessNo("12345")
		.description("첫번째 매장입니다.")
		.build();
		store.changeUser(findUser);
		Store createdStore = storeRepository.save(store);
		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
		
		// category
		MainCategory mainCategory = MainCategory.builder()
				.name("메인카테고리1")
				.build();
		mainCategory.changeStore(findStore);
		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
		
		SubCategory subCategory = SubCategory.builder()
				.name("서브카테고리1")
				.build();
		subCategory.changeMainCategory(findMainCategory);
		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
		
		// product
		ProductRequest productRequest = ProductRequest.builder()
				.name("상품1")
				.price(1000)
				.code("M0001")
				.quantity(100)
				.isSoldOut(false)
				.priceControlType(PriceControlType.MAINMALL)
				.build();
		
		OptionRequest optionRequest = OptionRequest.builder()
				.name("옵션1")
				.build();
		
		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
				.name("옵션그룹1")
				.build();
		
		optionGroupRequest.getOptionRequests().add(optionRequest);
		productRequest.getOptionGroupRequests().add(optionGroupRequest);
		
		Product product = productRequest.toEntity(productRequest);
		
		product.changeStore(findStore);
		product.changeSubCategory(findSubCategory);
		
		List<OptionGroup> optionGroups = productRequest.getOptionGroupRequests().stream()
				.map(ogr -> {
					OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
					List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
					options.forEach(opt -> optionGroup.addOption(opt));
					return optionGroup;
				})
				.collect(Collectors.toList());
		
		optionGroups.forEach(og -> product.addOptionGroup(og));
		
		Product createdProduct = productRepository.save(product);
		
		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
		
		ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
				.name("상품2")
				.price(2000)
				.isSoldOut(true)
				.priceControlType(PriceControlType.SUBMALL)
				.build();
		
		Product updateProduct = productUpdateRequest.toEntity(productUpdateRequest);
		
		// when
		TempProduct tempProduct = TempProduct.toTempProduct(findProduct); // 원래 내용 임시 저장용 dto
		
		findProduct.update(updateProduct);
		log.info("update 쿼리 실행 시점 확인");

		eventPublisher.publishEvent(new UpdatedProductEvent(tempProduct, updateProduct));
		// UpdatedProductEvent.toEvent(orgProduct, updateProduct)
		
		// then
		Product renewProduct = productRepository.findById(findProduct.getId()).orElse(null);
		
		assertEquals(updateProduct.getName(), renewProduct.getName());
		assertEquals(updateProduct.getPrice(), renewProduct.getPrice());
		assertEquals(updateProduct.isSoldOut(), renewProduct.isSoldOut());
		assertEquals(updateProduct.getPriceControlType(), renewProduct.getPriceControlType());

	}
}
