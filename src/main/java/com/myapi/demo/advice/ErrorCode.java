package com.myapi.demo.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	
	BINDING_EXCEPTION("0001", "잘못 입력된 값이 있습니다."),
	
	NOT_FOUND_SUB_CATEGORY_EXCEPTION("A0001", "서브 카테고리 조회에 실패했습니다."),
	
	NOT_FOUND_STORE_EXCEPTION("A0002", "매장 조회에 실패했습니다.");
	
	private final String code;
	
	private final String message;
	
}
