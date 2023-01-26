package com.myapi.demo.request;

import javax.validation.constraints.NotBlank;

import com.myapi.demo.domain.SubCategory;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SubCategoryRequest {
	
	@NotBlank(message = "카테고리 이름을 입력하세요.")
	private String name;
	
	public SubCategory toEntity(SubCategoryRequest request) {
		
		return SubCategory.builder()
		.name(request.getName())
		.build();
		
	}
}
