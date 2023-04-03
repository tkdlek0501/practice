package com.myapi.demo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OptionGroupUpdateRequest {
	
	@NotBlank(message = "옵션 그룹 이름을 입력해주세요.")
	private String name;
	
	@NotNull(message = "옵션 그룹 필수 여부를 체크해주세요.")
	private Boolean isRequired;
	
}
