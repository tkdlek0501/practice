package com.myapi.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.exception.BindingException;
import com.myapi.demo.exception.NotFoundStoreException;
import com.myapi.demo.exception.NotFoundSubCategoryException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {
	
	private ErrorResponse extracted(Exception e, ErrorCode errorCode) {
		ErrorResponse errorResponse = ErrorResponse.builder()
		.code(errorCode.getCode())
		.message(e.getMessage() != null ? e.getMessage() : errorCode.getMessage())
		.build();
		return errorResponse;
	}
	
	@ExceptionHandler(BindingException.class)
	public ResponseEntity<ErrorResponse> handleBindingException(BindingException e){
		ErrorResponse errorResponse = extracted(e, ErrorCode.BINDING_EXCEPTION);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(NotFoundSubCategoryException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundSubCategoryException(NotFoundSubCategoryException e){
		ErrorResponse errorResponse = extracted(e, ErrorCode.NOT_FOUND_SUB_CATEGORY_EXCEPTION);
				
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(NotFoundStoreException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundStoreException(NotFoundStoreException e){
		ErrorResponse errorResponse = extracted(e, ErrorCode.NOT_FOUND_STORE_EXCEPTION);
				
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
