package com.myapi.demo.repository.custom;

import java.util.List;

import com.myapi.demo.dto.ProductSearchCondition;
import com.myapi.demo.dto.ProductSearchDto;

public interface ProductRepositoryCustom {
	
	List<ProductSearchDto> search(ProductSearchCondition condition);
	
	
}
