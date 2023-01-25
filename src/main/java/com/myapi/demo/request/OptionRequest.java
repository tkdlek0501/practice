package com.myapi.demo.request;

import javax.validation.constraints.NotBlank;

import com.myapi.demo.domain.Option;

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
public class OptionRequest {
	
	@NotBlank(message = "옵션 이름을 입력해주세요.")
	private String name;
	
	public Option toEntity(OptionRequest optionRequest) {
		return Option.builder()
		.name(optionRequest.getName())
		.build();
	}
}
