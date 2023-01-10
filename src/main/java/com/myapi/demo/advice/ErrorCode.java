package com.myapi.demo.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	
	Binding_Exception("0001", "잘못 입력된 값이 있습니다.");
	
	private final String code;
	
	private final String message;
	
}
