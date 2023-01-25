package com.myapi.demo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class ProductCreateRequest {
	
	private ProductRequest productRequest;
	
	private OptionGroupRequest optionGroupRequest;
	
	private OptionRequest optionRequest;
	
}
