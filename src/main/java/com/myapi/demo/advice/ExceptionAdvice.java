package com.myapi.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.exception.BindingException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {
	
	@ExceptionHandler(BindingException.class)
	public ResponseEntity<ErrorResponse> handleBindingException(BindingException e){
		ErrorResponse errorResponse = ErrorResponse.builder()
		.code(ErrorCode.Binding_Exception.getCode())
		.message(e.getMessage() != null ? e.getMessage() : ErrorCode.Binding_Exception.getMessage())
		.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
