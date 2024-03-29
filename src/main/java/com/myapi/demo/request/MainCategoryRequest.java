package com.myapi.demo.request;

import javax.validation.constraints.NotBlank;

import com.myapi.demo.domain.MainCategory;
import com.myapi.demo.domain.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class MainCategoryRequest {
	
	@NotBlank(message = "카테고리 이름을 입력하세요.")
	private String name;
	
	public MainCategory toEntity(MainCategoryRequest request, Store store) {
		
		return MainCategory.builder()
		.name(request.getName())
		.store(store)
		.build();
	}
	
}
