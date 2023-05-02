package com.myapi.demo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.myapi.demo.domain.Option;
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
public class OptionRequest {
	
	@NotBlank(message = "옵션 이름을 입력해주세요.")
	private String name;
	
	@NotNull(message = "옵션 수량을 입력해주세요.")
	private Integer quantity;
	
	@NotNull(message = "옵션 품절 여부를 체크해주세요.")
	private Boolean isSoldOut;
	
	public static Option toEntity(OptionRequest optionRequest, OptionGroup optionGroup) {
		return Option.builder()
		.optionGroup(optionGroup)
		.name(optionRequest.getName())
		.quantity(optionRequest.getQuantity())
		.isSoldOut(optionRequest.getIsSoldOut())
		.build();
	}
}
