package com.myapi.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.Option;
import com.myapi.demo.domain.OptionGroup;
import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;
import com.myapi.demo.domain.Store;
import com.myapi.demo.domain.User;
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
	
	@Transactional
	public void create(ProductCreateRequest request) {
		
		if(request.getProductRequest().getOptionGroupRequests().size() < 1) {
			throw new NotSatisfiedCreateOptionGroupConditionException(null); // 상품은 최소 한 개의 옵션 그룹을 가져야 합니다.
		}
		
		request.getProductRequest().getOptionGroupRequests().stream()
		.map(ogr -> {
			(ogr.getOptionRequests().size() < 1) { 
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

	public void update(ProductUpdateRequest request) {
		
		Product product = productRepository.findById(request.getId()).orElseThrow(() -> new NotFoundProductException(null));
		TempProduct tempProduct = TempProduct.toTempProduct(product);
		
		Product updateProduct = request.toEntity(request);
		product.update(updateProduct);
		
		eventPublisher.publishEvent(new UpdatedProductEvent(tempProduct, updateProduct));
	}
	
	public List<MainMallProductResponse> findMainMallProduct(User user) {
		
		Store store = storeRepository.findByUser(user);
		
		Store mainStore = storeRepository.findById(store.getMainStore().getId()).orElseThrow(() -> new NotFoundStoreException(null));
		
		// 메인몰 상품
		List<Product> mainProducts = productRepository.findByStoreAndPriceControlTypeAndExpiredAtIsNull(mainStore, PriceControlType.MAINMALL);
	
		// 자기 상품
		List<Product> subProducts = productRepository.findByStoreAndExpiredAtIsNull(store);
		List<String> subProductCodes = subProducts.stream().map(s -> s.getCode()).collect(Collectors.toList());
		
		return mainProducts.stream().filter(m -> !subProductCodes.contains(m.getCode())).map(MainMallProductResponse::of).collect(Collectors.toList());
	}

}
