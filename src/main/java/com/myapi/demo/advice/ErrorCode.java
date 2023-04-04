package com.myapi.demo.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	
	BINDING_EXCEPTION("0001", "잘못 입력된 값이 있습니다."),
	
	NOT_FOUND_SUB_CATEGORY_EXCEPTION("A0001", "서브 카테고리 조회에 실패했습니다."),
	
	NOT_FOUND_STORE_EXCEPTION("A0002", "매장 조회에 실패했습니다."),
	
	NOT_FOUND_OPTION_GROUP_EXCEPTION("A0003", "옵션그룹 조회에 실패했습니다."),
	
	NOT_FOUND_OPTION_EXCEPTION("A0004", "옵션 조회에 실패했습니다."),
	
	NOT_FOUND_USER_EXCEPTION("A0005", "회원 조회에 실패했습니다."),
	
	NOT_SATISFIED_CREATE_OPTION_CONDITION_EXCEPTION("B0001", "옵션 그룹 생성시 1개 이상의 옵션이 포함되어야 합니다."),
	
	NOT_SATISFIED_DELETE_OPTION_GROUP_CONDITION_EXCEPTION("B0002", "옵션 그룹은 상품에 최소 1개가 포함되어야 합니다."),
	
	NOT_SATISFIED_DELETE_OPTION_CONDITION_EXCEPTION("B0003", "옵션은 옵션 그룹에 최소 1개가 포함되어야 합니다."),
	
	NOT_SATISFIED_CREATE_OPTION_GROUP_CONDITION_EXCEPTION("B0004", "옵션은 상품에 최소 1개가 포함되어야 합니다.");
	
	private final String code;
	
	private final String message;
	
}
