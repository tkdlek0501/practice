package com.myapi.demo.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	
	@NotNull(message = "필수 여부를 체크해주세요.")
	private Boolean isRequired;
	
	@Builder.Default
	private List<OptionRequest> optionRequests = new ArrayList<>();
	
	public static OptionGroup toEntity(OptionGroupRequest optionGroupRequest) {
		
		return OptionGroup.builder()
		.name(optionGroupRequest.getName())
		.isRequired(optionGroupRequest.getIsRequired())
		.options(new ArrayList<>())
		.build();
	}
}
