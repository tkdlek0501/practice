package com.myapi.demo.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.myapi.demo.domain.Product;
import com.myapi.demo.domain.SubCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class MainMallProductResponse {
	
	private String name;
	
	private int price;
	
	private String code;
	
	private SubCategory subCategory;
	
	List<OptionGroupResponse> optionGroupResponses = new ArrayList<>();
	
	public static MainMallProductResponse of(Product product){
		
		List<OptionGroupResponse> optionGroupResponses = new ArrayList<>();
		optionGroupResponses = product.getOptionGroups().stream().filter(og -> og.getExpiredAt() == null).map(OptionGroupResponse::of).collect(Collectors.toList());
		
		return MainMallProductResponse.builder()
				.name(product.getName())
				.price(product.getPrice())
				.code(product.getCode())
				.subCategory(product.getSubCategory())
				.optionGroupResponses(optionGroupResponses)
				.build();
	}
	
}
