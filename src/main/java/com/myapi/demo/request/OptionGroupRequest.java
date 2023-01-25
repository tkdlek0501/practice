package com.myapi.demo.request;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;

import com.myapi.demo.domain.OptionGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroupRequest {
	
	@NotBlank(message = "옵션그룹 이름을 입력해주세요.")
	private String name;
	
	public OptionGroup toEntity(OptionGroupRequest optionGroupRequest) {
		
		return OptionGroup.builder()
		.name(optionGroupRequest.getName())
		.options(new ArrayList<>())
		.build();
	}
}
