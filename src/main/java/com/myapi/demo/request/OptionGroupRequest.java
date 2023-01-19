package com.myapi.demo.request;

import java.util.ArrayList;

import com.myapi.demo.domain.OptionGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class OptionGroupRequest {
	
	private String name;
	
	public OptionGroup toEntity(OptionGroupRequest optionGroupRequest) {
		
		return OptionGroup.builder()
		.name(optionGroupRequest.getName())
		.options(new ArrayList<>())
		.build();
	}
}
