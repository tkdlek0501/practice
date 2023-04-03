package com.myapi.demo.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.myapi.demo.domain.OptionGroup;

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
public class OptionGroupResponse {
	
	private String name;
	
	private boolean isRequired;
	
	@Builder.Default
	private List<OptionResponse> optionResponses = new ArrayList<>();
	
	public static OptionGroupResponse of(OptionGroup optionGroup) {
		
		List<OptionResponse> optionResponses = new ArrayList<>();
		optionResponses = optionGroup.getOptions().stream().filter(o -> o.getExpiredAt() == null).map(OptionResponse::of).collect(Collectors.toList());
		
		return OptionGroupResponse.builder()
				.name(optionGroup.getName())
				.isRequired(optionGroup.isRequired())
				.optionResponses(optionResponses)
				.build();
	}
}
