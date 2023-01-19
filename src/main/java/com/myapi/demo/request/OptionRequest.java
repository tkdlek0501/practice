package com.myapi.demo.request;

import com.myapi.demo.domain.Option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class OptionRequest {
	
	private String name;
	
	public Option toEntity(OptionRequest optionRequest) {
		return Option.builder()
		.name(optionRequest.getName())
		.build();
	}
}
