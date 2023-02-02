package com.myapi.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.Option;
import com.myapi.demo.domain.OptionGroup;
import com.myapi.demo.domain.Product;
import com.myapi.demo.domain.Store;
import com.myapi.demo.domain.SubCategory;
import com.myapi.demo.dto.ProductSearchCondition;
import com.myapi.demo.dto.ProductSearchDto;
import com.myapi.demo.repository.ProductRepository;
import com.myapi.demo.repository.StoreRepository;
import com.myapi.demo.repository.SubCategoryRepository;
import com.myapi.demo.request.OptionGroupRequest;
import com.myapi.demo.request.OptionRequest;
import com.myapi.demo.request.ProductCreateRequest;

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
	
	@Transactional
	public void create(ProductCreateRequest request) {
		
		log.info("ProductCreateRequest : {}", request);
		
		SubCategory subCategory = subCategoryRepository.findById(request.getProductRequest().getSubCategoryId()).orElse(null);
		Store store = storeRepository.findById(request.getProductRequest().getStoreId()).orElse(null);
		
		Product product = request.getProductRequest().toEntity(request.getProductRequest());
		
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
	
}
