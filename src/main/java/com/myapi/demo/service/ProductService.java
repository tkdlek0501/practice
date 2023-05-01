package com.myapi.demo.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.MainCategory;
import com.myapi.demo.domain.Option;
import com.myapi.demo.domain.OptionGroup;
import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;
import com.myapi.demo.domain.Store;
import com.myapi.demo.domain.User;
import com.myapi.demo.domain.UserType;
import com.myapi.demo.domain.SubCategory;
import com.myapi.demo.dto.ProductSearchCondition;
import com.myapi.demo.dto.ProductSearchDto;
import com.myapi.demo.dto.TempProduct;
import com.myapi.demo.event.dto.UpdatedProductEvent;
import com.myapi.demo.exception.NotFoundProductException;
import com.myapi.demo.exception.NotFoundStoreException;
import com.myapi.demo.exception.NotFoundSubCategoryException;
import com.myapi.demo.exception.NotSatisfiedCreateOptionConditionException;
import com.myapi.demo.exception.NotSatisfiedCreateOptionGroupConditionException;
import com.myapi.demo.exception.NotSatisfiedUserTypeException;
import com.myapi.demo.repository.MainCategoryRepository;
import com.myapi.demo.repository.ProductRepository;
import com.myapi.demo.repository.StoreRepository;
import com.myapi.demo.repository.SubCategoryRepository;
import com.myapi.demo.request.OptionGroupRequest;
import com.myapi.demo.request.OptionRequest;
import com.myapi.demo.request.ProductCreateRequest;
import com.myapi.demo.request.ProductUpdateRequest;
import com.myapi.demo.response.MainMallProductResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {
	
	private final ProductRepository productRepository;

	private final StoreRepository storeRepository;

	private final SubCategoryRepository subCategoryRepository;

	private final ApplicationEventPublisher eventPublisher;
	
	private final MainCategoryRepository mainCategoryRepository;
	
	@Transactional
	public void create(ProductCreateRequest request) {
		
		if(request.getProductRequest().getOptionGroupRequests().size() < 1) {
			throw new NotSatisfiedCreateOptionGroupConditionException(null); // 상품은 최소 한 개의 옵션 그룹을 가져야 합니다.
		}
		
		request.getProductRequest().getOptionGroupRequests().forEach(ogr -> {
			if(ogr.getOptionRequests().size() < 1) {
				throw new NotSatisfiedCreateOptionConditionException(null); // 옵션 그룹 내에는 최소 1개의 옵션이 있어야 합니다.
			}
		});
		
		SubCategory subCategory = subCategoryRepository.findById(request.getProductRequest().getSubCategoryId())
				.orElseThrow(() -> new NotFoundSubCategoryException(null));
				
		Store store = storeRepository.findById(request.getProductRequest().getStoreId())
				.orElseThrow(() -> new NotFoundStoreException(null));
		
		// 상품을 생성하는 주체
		String owner = "";
		if(store.getMainStore() == null) {
			owner = "M";
		}else {
			owner = "S";
		}
		
		Product product = request.getProductRequest().toEntity(request.getProductRequest(), owner);
		
		product.changeStore(store);
		product.changeSubCategory(subCategory);
		
		List<OptionGroup> optionGroups = request.getProductRequest().getOptionGroupRequests().stream()
				.map(ogr -> {
					OptionGroup optionGroup = OptionGroupRequest.toEntity(ogr);
					List<Option> options = ogr.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
					options.forEach(opt -> optionGroup.addOption(opt));
					return optionGroup;
				}).collect(Collectors.toList());
		
		optionGroups.forEach(og -> product.addOptionGroup(og));
		
		productRepository.save(product);
	}
	
	
	public List<ProductSearchDto> search(ProductSearchCondition request){
		
		return productRepository.search(request);
		
	}
	
	@Transactional
	public void update(ProductUpdateRequest request, User user) {
		
		Product product = productRepository.findById(request.getId()).orElseThrow(() -> new NotFoundProductException(null));
		// 1. 메인몰이 관리하는 상품은 메인몰만 수정 가능
		if(product.getPriceControlType().equals(PriceControlType.MAINMALL) && !user.getType().equals(UserType.MAINMALL)) {
			throw new NotSatisfiedUserTypeException(null);
		}
		
		TempProduct tempProduct = TempProduct.toTempProduct(product);
		
		Product updateProduct = request.toEntity(request);
		product.update(updateProduct);
		
		// 상품 수정 내역을 관리하기 위한 이벤트
		eventPublisher.publishEvent(new UpdatedProductEvent(tempProduct, updateProduct));
	}
	
	public List<MainMallProductResponse> findMainMallProduct(User user) {
		
		Store store = storeRepository.findByUser(user).orElseThrow(() -> new NotFoundStoreException(null));
		
		Store mainStore = storeRepository.findById(store.getMainStore().getId()).orElseThrow(() -> new NotFoundStoreException(null));
		
		// 메인몰 상품
		List<Product> mainProducts = productRepository.findByStoreAndPriceControlTypeAndExpiredAtIsNull(mainStore, PriceControlType.MAINMALL);
	
		// 자기 상품
		List<Product> subProducts = productRepository.findByStoreAndExpiredAtIsNull(store);
		List<String> subProductCodes = subProducts.stream().map(s -> s.getCode()).collect(Collectors.toList());
		
		return mainProducts.stream().filter(m -> !subProductCodes.contains(m.getCode())).map(MainMallProductResponse::of).collect(Collectors.toList());
	}
	
	@Transactional
	public void updateToMainMallProduct(User user) {
		
		Store store = storeRepository.findByUser(user).orElseThrow(() -> new NotFoundStoreException(null));
		
		Store mainStore = storeRepository.findById(store.getMainStore().getId()).orElseThrow(() -> new NotFoundStoreException(null));
		
		// 메인몰 상품
		List<Product> mainProducts = productRepository.findByStoreAndPriceControlTypeAndExpiredAtIsNull(mainStore, PriceControlType.MAINMALL);
	
		// 자기 상품
		List<Product> subProducts = productRepository.findByStoreAndExpiredAtIsNull(store);
		
		List<String> mainProductCodes = mainProducts.stream().map(m -> m.getCode()).collect(Collectors.toList());
		List<String> subProductCodes = subProducts.stream().map(s -> s.getCode()).collect(Collectors.toList());
		
		// 임시 카테고리; 임시 카테고리를 강제 생성해서 넣어준다
		MainCategory mainCategory = MainCategory.builder()
				.name("임시")
				.store(store)
				.build();
		mainCategoryRepository.save(mainCategory);
		
		SubCategory subCategory = SubCategory.builder()
				.name("임시")
				.build();
		SubCategory newSub = subCategoryRepository.save(subCategory);
		
		// 1. 메인몰 상품 중 없는 것은 save
		List<Product> newProducts = mainProducts.stream().filter(m -> !subProductCodes.contains(m.getCode())).collect(Collectors.toList());
		
		newProducts.stream().map(p -> {
			p.setStore(store);
			p.changeSubCategory(newSub);
			return p;
		}).collect(Collectors.toList());
		
		productRepository.saveAll(newProducts);
		
		// TODO: 하위에 있는 옵션도 모두 가져오기
		
		
		// 2. 메인몰 상품 중 있는 것은 update
		List<Product> updateProducts = subProducts.stream().filter(s -> mainProductCodes.contains(s.getCode())).collect(Collectors.toList());
		Map<String, Product> mainProductsMap = mainProducts.stream().collect(Collectors.toMap(Product::getCode, Function.identity()));
		
		updateProducts.forEach(p -> {
			Product product = mainProductsMap.get(p.getCode());
			p.updateToMain(product);
		});
		
		// TODO: 하위에 있는 옵션도 모두 가져오기
	}

}
