package com.myapi.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

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
import com.myapi.demo.repository.OptionGroupRepository;
import com.myapi.demo.repository.OptionRepository;
import com.myapi.demo.repository.ProductRepository;
import com.myapi.demo.repository.StoreRepository;
import com.myapi.demo.repository.SubCategoryRepository;
import com.myapi.demo.repository.UserRepository;
import com.myapi.demo.request.OptionGroupRequest;
import com.myapi.demo.request.OptionGroupUpdateRequest;
import com.myapi.demo.request.OptionRequest;
import com.myapi.demo.request.OptionUpdateRequest;
import com.myapi.demo.request.ProductRequest;
import com.myapi.demo.request.ProductUpdateRequest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class ProductServiceTest {
	
//	@Autowired ProductRepository productRepository;
//	
//	@Autowired StoreRepository storeRepository;
//	
//	@Autowired MainCategoryRepository mainCategoryRepository;
//	
//	@Autowired SubCategoryRepository subCategoryRepository;
//	
//	@Autowired UserRepository userRepository;
//	
//	@Autowired PasswordEncoder passwordEncoder;
//	
//	@Autowired ApplicationEventPublisher eventPublisher;
//	
//	@Autowired OptionGroupRepository optionGroupRepository;
//	
//	@Autowired OptionRepository optionRepository;
//	
//	@Autowired EntityManager em;
//	
//	// 상품 생성
//	@Test
//	@Rollback(false)
//	public void create() {
//		// given
//		
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		// 위에 까지 request 형식
//		
//		// when
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt);
//			});
//		});
//		
//		// then
//		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
//		
//		assertEquals(createdProduct, findProduct);
//		assertEquals(createdProduct.getOptionGroups().get(0), findProduct.getOptionGroups().get(0));
//		log.info("option : {}", findProduct.getOptionGroups().get(0).getOptions().get(0));
//		assertEquals(createdProduct.getOptionGroups().get(0).getOptions().get(0), findProduct.getOptionGroups().get(0).getOptions().get(0));
//	}
//	
//	// TODO: impl 수정 필요
//	@Test
//	public void search() {
//		// given
//		
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt);
//			});
//		});
//		
//		log.info("product : {}", createdProduct);
//
//		ProductSearchCondition condition = ProductSearchCondition.builder()
//				.priceGoe(500)
//				.priceLoe(2000)
//				.build();
//		
//		
//		// when
//		List<ProductSearchDto> productSearchDto = productRepository.search(condition);
//		
//		// then
//		log.info("productSearchDto : {}", productSearchDto);
//	}
//	
//	@Test
//	@Rollback(false)
//	public void update() {
//		// given
//		
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt);
//			});
//		});
//		
//		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
//		
//		ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
//				.name("상품2")
//				.price(2000)
//				.isSoldOut(true)
//				.priceControlType(PriceControlType.SUBMALL)
//				.build();
//		
//		Product updateProduct = productUpdateRequest.toEntity(productUpdateRequest);
//		
//		// when
//		TempProduct tempProduct = TempProduct.toTempProduct(findProduct); // 원래 내용 임시 저장용 dto
//		
//		findProduct.update(updateProduct);
//		log.info("update 쿼리 실행 시점 확인");
//
//		eventPublisher.publishEvent(new UpdatedProductEvent(tempProduct, updateProduct));
//		// UpdatedProductEvent.toEvent(orgProduct, updateProduct)
//		
//		// then
//		Product renewProduct = productRepository.findById(findProduct.getId()).orElse(null);
//		
//		assertEquals(updateProduct.getName(), renewProduct.getName());
//		assertEquals(updateProduct.getPrice(), renewProduct.getPrice());
//		assertEquals(updateProduct.isSoldOut(), renewProduct.isSoldOut());
//		assertEquals(updateProduct.getPriceControlType(), renewProduct.getPriceControlType());
//
//	}
//	
//	// 옵션은 개별적으로 추가, 수정, 삭제 api로 관리
//	// 옵션그룹 추가 (옵션까지 추가) - 추가시에는 defualt로 옵션그룹과 옵션이 1개씩 추가된다
//	@Test
//	@Rollback(false)
//	public void 옵션_그룹_추가() {
//		// 어떤 상품에 옵션 그룹을 추가할 건지
//		// given
//
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		// when
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt);
//			});
//		});
//		// 위에 까지 상품 등록
//		
//		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
//		
//		OptionRequest addOptionRequest = OptionRequest.builder()
//				.name("옵션 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroupRequest addOptionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션 그룹 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroup addOptionGroup = OptionGroupRequest.toEntity(addOptionGroupRequest);
//		addOptionGroup.changeProduct(findProduct);
//		
//		Option addOption = OptionRequest.toEntity(addOptionRequest);
//		
//		// 옵션 그룹 추가
//		addOptionGroup = optionGroupRepository.save(addOptionGroup);
//		
//		 // 옵션 추가
//		addOption.changeOptionGroup(addOptionGroup);
//		optionRepository.save(addOption);
//	}
//	
//	@Test
//	@Rollback(false)
//	public void 옵션_개별_추가() {
//		// given
//
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt);
//			});
//		});
//		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
//		// 위에 까지 상품 등록(옵션그룹, 옵션 포함) 로직
//		
//		// when
//		// 옵션 그룹에 옵션 개별적으로 추가
//		OptionGroup orgOptionGroup = findProduct.getOptionGroups().get(0); // 만들어진 옵션그룹 가져오기
//		Option addOption = Option.builder()
//				.name("추가 옵션")
//				.build();// 추가할 옵션
//		addOption.changeOptionGroup(orgOptionGroup);
//		optionRepository.save(addOption);
//	}
//	
//	//옵션그룹 개별 삭제 - 하위 옵션까지 삭제 ; delete 사용 안할 것임
//	@Test
//	@Rollback(false)
//	public void 옵션그룹_개별_삭제() {
//		// given
//	
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt);
//			});
//		});
//		
//		OptionRequest addOptionRequest = OptionRequest.builder()
//				.name("옵션 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroupRequest addOptionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션 그룹 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroup addOptionGroup = OptionGroupRequest.toEntity(addOptionGroupRequest);
//		addOptionGroup.changeProduct(createdProduct);
//		optionGroupRepository.save(addOptionGroup);
//		
//		Option addOption = OptionRequest.toEntity(addOptionRequest);
//		addOption.changeOptionGroup(addOptionGroup);
//		optionRepository.save(addOption);
//		
//		// when
//		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
//		// TODO: issue: https://velog.io/@kimsunfang/%EC%A0%91%EA%B7%BC%EC%A0%9C%ED%95%9C%EC%9E%90%EC%99%80-JPA
//		// jpa 는 private한 기본생성자를 가진 엔티티에 대해서는 프록시 객체를 만들지 못한다
//		
//		// 옵션그룹 삭제 : 옵션 그룹 내 옵션 부터 모두 삭제
//		if(findProduct.getOptionGroups().size() > 1) { // 1개 이상일 때만 삭제 가능
//			findProduct.getOptionGroups().get(0).getOptions().forEach(o -> optionRepository.delete(o));
//			optionGroupRepository.delete(findProduct.getOptionGroups().get(0));
//		}
//	}
//	
//	// 옵션그룹 개별 삭제 - 하위 옵션까지 삭제; delete 대신 update
//	@Test
//	@Rollback(false)
//	public void 옵션그룹_개별_expire() {
//		// given
//	
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt);
//			});
//		});
//		
//		OptionRequest addOptionRequest = OptionRequest.builder()
//				.name("옵션 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroupRequest addOptionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션 그룹 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroup addOptionGroup = OptionGroupRequest.toEntity(addOptionGroupRequest);
//		addOptionGroup.changeProduct(createdProduct);
//		optionGroupRepository.save(addOptionGroup);
//		
//		Option addOption = OptionRequest.toEntity(addOptionRequest);
//		addOption.changeOptionGroup(addOptionGroup);
//		optionRepository.save(addOption);
//		
//		// when
//		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
//		
//		// 옵션그룹 삭제 : 옵션 그룹 내 옵션 부터 모두 삭제
//		if(findProduct.getOptionGroups().size() > 1) { // 1개 이상일 때만 삭제 가능
////			findProduct.getOptionGroups().get(0).getOptions().forEach(o -> optionRepository.delete(o));
////			optionGroupRepository.delete(findProduct.getOptionGroups().get(0));
//			findProduct.getOptionGroups().get(0).getOptions().forEach(o -> o.expire());
//			findProduct.getOptionGroups().get(0).expire();
//		}
//	}
//	
//	@Test
//	@Rollback(false)
//	public void 옵션_개별_삭제() {
//		// given
//	
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt); // TODO: saveAll 로 바꾸기
//			});
//		});
//		
//		OptionRequest addOptionRequest = OptionRequest.builder()
//				.name("옵션 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroupRequest addOptionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션 그룹 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroup addOptionGroup = OptionGroupRequest.toEntity(addOptionGroupRequest);
//		addOptionGroup.changeProduct(createdProduct);
//		optionGroupRepository.save(addOptionGroup);
//		
//		Option addOption = OptionRequest.toEntity(addOptionRequest);
//		addOption.changeOptionGroup(addOptionGroup);
//		List<Option> options = new ArrayList<>();
//		options.add(addOption);
//		
//		// 두번째 옵션그룹에 옵션 하나 더 추가
//		OptionRequest addOptionRequest2 = OptionRequest.builder()
//				.name("옵션 이름을 변경해주세요2.")
//				.build();
//		Option addOption2 = OptionRequest.toEntity(addOptionRequest2);
//		addOption2.changeOptionGroup(addOptionGroup);
//		options.add(addOption2);
//		
////		options.add(addOption); 
//
//		optionRepository.saveAll(options);
//		
//		// when
//		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
//		
//		// 옵션 삭제 (두번째 옵션그룹)
//		List<Long> ids = new ArrayList<>(); // 받아온 id list (test용)
//		for(Option o : findProduct.getOptionGroups().get(1).getOptions()) {
//			ids.add(o.getId());
//		}
//		
//		// 실제로는 하나씩 delete 하는 api 이기 때문에
//		// 처음 delete 요청시
//		if(findProduct.getOptionGroups().get(1).getOptions().size() > 1) {
//			log.info("삭제 진행");
//			optionRepository.delete(optionRepository.findById(ids.get(0)).orElse(null));
//			em.flush();
//			em.clear();
//			findProduct = productRepository.findById(createdProduct.getId()).orElse(null); // 다시 조회
//		}
//		
//		// 다음 delete 요청시
//		if(findProduct.getOptionGroups().get(1).getOptions().size() > 1) {
//			log.info("삭제 진행");
//			optionRepository.delete(optionRepository.findById(ids.get(1)).orElse(null));
//			em.flush();
//			em.clear();
//			findProduct = productRepository.findById(createdProduct.getId()).orElse(null); // 다시 조회
//		}
//		
//		// *delete 후에는 다시 조회해와야 한다 (조회된 시점에 스냅샷에 고정돼있기 때문에 갱신해줘야 한다)
//		// 즉 select 이후 update나 delete이후에는 em.flush, em.clear를 하고 다시 조회해서 갱신해줘야 한다
//		// 실제 service 로직 에서는 option으로 부터 optionGroup을 조회해서 size 체크를 하고 진행하는 로직으로 만들기
//		
//		log.info("res option size : {}", findProduct.getOptionGroups().get(0).getOptions().size()); // 1로 남아있어야 한다
//	}
//	
//	@Test
//	@Rollback(false)
//	public void 옵션_개별_expire() {
//		// given
//	
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt); // TODO: saveAll 로 바꾸기
//			});
//		});
//		
//		OptionRequest addOptionRequest = OptionRequest.builder()
//				.name("옵션 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroupRequest addOptionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션 그룹 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroup addOptionGroup = OptionGroupRequest.toEntity(addOptionGroupRequest);
//		addOptionGroup.changeProduct(createdProduct);
//		optionGroupRepository.save(addOptionGroup);
//		
//		Option addOption = OptionRequest.toEntity(addOptionRequest);
//		addOption.changeOptionGroup(addOptionGroup);
//		List<Option> options = new ArrayList<>();
//		options.add(addOption);
//		
//		// 두번째 옵션그룹에 옵션 하나 더 추가
//		OptionRequest addOptionRequest2 = OptionRequest.builder()
//				.name("옵션 이름을 변경해주세요2.")
//				.build();
//		Option addOption2 = OptionRequest.toEntity(addOptionRequest2);
//		addOption2.changeOptionGroup(addOptionGroup);
//		options.add(addOption2);
//
//		optionRepository.saveAll(options);
//		
//		// when
//		Product findProduct = productRepository.findById(createdProduct.getId()).orElse(null);
//		OptionGroup findOptionGroup = optionGroupRepository.findById(findProduct.getOptionGroups().get(1).getId()).orElse(null);
//		
//		// 옵션 삭제 (두번째 옵션그룹)
//		List<Long> ids = new ArrayList<>(); // 받아온 id list (test용)
//		for(Option o : findProduct.getOptionGroups().get(1).getOptions()) {
//			ids.add(o.getId());
//		}
//		
//		// 실제로는 하나씩 delete 하는 api 이기 때문에
//		// 처음 delete 요청시
//		if(findOptionGroup.getOptions().stream().filter(o -> o.getExpiredAt() == null).collect(Collectors.toList()).size() > 1) {
//			optionRepository.findById(ids.get(0)).orElse(null).expire();
//			em.flush();
//			em.clear();
//			findOptionGroup = optionGroupRepository.findById(findProduct.getOptionGroups().get(1).getId()).orElse(null);
//		}
//		
//		// 다음 delete 요청시
//		if(findOptionGroup.getOptions().stream().filter(o -> o.getExpiredAt() == null).collect(Collectors.toList()).size() > 1) {
//			optionRepository.findById(ids.get(1)).orElse(null).expire();
//			em.flush();
//			em.clear();
//			findOptionGroup = optionGroupRepository.findById(findProduct.getOptionGroups().get(1).getId()).orElse(null);
//		}
//	}
//	
//	// 옵션그룹 개별 수정
//	@Test
//	@Rollback(false)
//	public void 옵션그룹_개별_수정() {
//		// given
//	
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt); // TODO: saveAll 로 바꾸기
//			});
//		});
//		
//		OptionRequest addOptionRequest = OptionRequest.builder()
//				.name("옵션 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroupRequest addOptionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션 그룹 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroup addOptionGroup = OptionGroupRequest.toEntity(addOptionGroupRequest);
//		addOptionGroup.changeProduct(createdProduct);
//		OptionGroup resOptionGroup = optionGroupRepository.save(addOptionGroup);
//		
//		Option addOption = OptionRequest.toEntity(addOptionRequest);
//		addOption.changeOptionGroup(addOptionGroup);
//		optionRepository.save(addOption);
//		
//		// when
//		em.flush();
//		em.clear();
//		OptionGroup findOptionGroup = optionGroupRepository.findById(resOptionGroup.getId()).orElse(null);
//		OptionGroupUpdateRequest request = new OptionGroupUpdateRequest("변경한 옵션그룹명1");
//		
//		findOptionGroup.update(request);
//
//	}
//	
//	// 옵션 개별 수정
//	@Test
//	@Rollback(false)
//	public void 옵션_개별_수정() {
//		// given
//	
//		// user
//		User user = User.builder()
//				.username("이름1")
//				.password(passwordEncoder.encode("abcde"))
//				.nickname("닉네임1")
//				.type(UserType.MAINMALL)
//				.build();
//		User createdUser = userRepository.save(user);
//		User findUser = userRepository.findById(createdUser.getId()).orElse(null);
//		
//		// store
//		Store store = Store.builder()
//		.name("매장1")
//		.businessNo("12345")
//		.description("첫번째 매장입니다.")
//		.build();
//		store.changeUser(findUser);
//		Store createdStore = storeRepository.save(store);
//		Store findStore = storeRepository.findById(createdStore.getId()).orElse(null);
//		
//		// category
//		MainCategory mainCategory = MainCategory.builder()
//				.name("메인카테고리1")
//				.build();
//		mainCategory.changeStore(findStore);
//		MainCategory createdMainCategory = mainCategoryRepository.save(mainCategory);
//		MainCategory findMainCategory = mainCategoryRepository.findById(createdMainCategory.getId()).orElse(null);
//		
//		SubCategory subCategory = SubCategory.builder()
//				.name("서브카테고리1")
//				.build();
//		subCategory.changeMainCategory(findMainCategory);
//		SubCategory createdSubCategory = subCategoryRepository.save(subCategory);
//		SubCategory findSubCategory = subCategoryRepository.findById(createdSubCategory.getId()).orElse(null);
//		
//		// product
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("상품1")
//				.price(1000)
//				.code("M0001")
//				.quantity(100)
//				.isSoldOut(false)
//				.priceControlType(PriceControlType.MAINMALL)
//				.build();
//		
//		OptionRequest optionRequest = OptionRequest.builder()
//				.name("옵션1")
//				.build();
//		
//		OptionGroupRequest optionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션그룹1")
//				.build();
//		
//		optionGroupRequest.getOptionRequests().add(optionRequest);
//		productRequest.getOptionGroupRequests().add(optionGroupRequest);
//		
//		
//		// product 생성
//		Product product = productRequest.toEntity(productRequest);
//		product.changeStore(findStore);
//		product.changeSubCategory(findSubCategory);
//		Product createdProduct = productRepository.save(product);
//		
//		productRequest.getOptionGroupRequests().forEach(ogr -> {
//			// optionGroup 생성
//			OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
//			optionGroup.changeProduct(createdProduct);
//			OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
//			
//			List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
//			// option 생성
//			options.forEach(opt -> {
//				opt.changeOptionGroup(createdOptionGroup);
//				optionRepository.save(opt); // TODO: saveAll 로 바꾸기
//			});
//		});
//		
//		OptionRequest addOptionRequest = OptionRequest.builder()
//				.name("옵션 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroupRequest addOptionGroupRequest = OptionGroupRequest.builder()
//				.name("옵션 그룹 이름을 변경해주세요.")
//				.build();
//		
//		OptionGroup addOptionGroup = OptionGroupRequest.toEntity(addOptionGroupRequest);
//		addOptionGroup.changeProduct(createdProduct);
//		optionGroupRepository.save(addOptionGroup);
//		
//		Option addOption = OptionRequest.toEntity(addOptionRequest);
//		addOption.changeOptionGroup(addOptionGroup);
//		Option resOption = optionRepository.save(addOption);
//		
//		// when
//		em.flush();
//		em.clear();
//		Option findOption = optionRepository.findById(resOption.getId()).orElse(null);
//		// 1. 기존 옵션 expire
//		findOption.expire();
//		
//		// 2. update한 정보로 새로 생성
//		OptionUpdateRequest request = new OptionUpdateRequest("변경한 옵션명1");
//		Option updateOption = OptionUpdateRequest.toEntity(request);
//		updateOption.changeOptionGroup(addOptionGroup);
//		optionRepository.save(updateOption);
//		
//	}
}
