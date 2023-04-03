package com.myapi.demo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.myapi.demo.domain.Option;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OptionUpdateRequest {
	
	@NotBlank(message = "옵션 이름을 입력해주세요.")
	private String name;
	
	@NotNull(message = "옵션 수량을 입력해주세요.")
	private Integer quantity;
	
	@NotNull(message = "옵션 품절 여부를 체크해주세요.")
	private Boolean isSoldOut;
	
	public static Option toEntity(OptionUpdateRequest request) {
		return Option.builder()
		.name(request.getName())
		.quantity(request.getQuantity())
		.isSoldOut(request.getIsSoldOut())
		.build();
	}
	
}
