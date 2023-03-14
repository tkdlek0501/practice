package com.myapi.demo.request;

import com.myapi.demo.domain.Option;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OptionUpdateRequest {
	
	private String name;
	
	public static Option toEntity(OptionUpdateRequest request) {
		return Option.builder()
		.name(request.getName())
		.build();
	}
	
}
