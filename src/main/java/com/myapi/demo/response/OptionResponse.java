package com.myapi.demo.response;

import java.util.List;

import com.myapi.demo.domain.Option;

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
public class OptionResponse {
	
	private String name; 
	
	private int quantity;
	
	private boolean isSoldOut;
	
	public static OptionResponse of(Option option) {
		return OptionResponse.builder()
				.name(option.getName())
				.quantity(option.getQuantity())
				.isSoldOut(option.isSoldOut())
				.build();
	}
}
