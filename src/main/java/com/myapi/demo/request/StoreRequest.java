package com.myapi.demo.request;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;

import com.myapi.demo.domain.Store;
import com.myapi.demo.domain.User;
import com.sun.istack.NotNull;

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
public class StoreRequest {
	
	@NotBlank(message = "매장 이름을 입력하세요.")
	private String name;
	
	@NotBlank(message = "매장 설명을 입력하세요.")
	private String description;
	
	@NotBlank(message = "매장 사업자번호를 입력하세요.")
	private String businessNo;
	
	@NotNull
	private Long userId;
	
	public Store toEntity(StoreRequest storeRequest, User user) {
		return Store.builder()
		.name(storeRequest.getName())
		.description(storeRequest.getDescription())
		.businessNo(storeRequest.getBusinessNo())
		.user(user)
		.build();
	}
	
}
