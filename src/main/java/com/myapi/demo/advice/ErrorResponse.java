package com.myapi.demo.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
	
	private String code;
	
	private String message;
}
