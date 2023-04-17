package com.myapi.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.exception.BindingException;
import com.myapi.demo.exception.NotFoundOptionException;
import com.myapi.demo.exception.NotFoundOptionGroupException;
import com.myapi.demo.exception.NotFoundStoreException;
import com.myapi.demo.exception.NotFoundSubCategoryException;
import com.myapi.demo.exception.NotFoundUserException;
import com.myapi.demo.exception.NotSatisfiedCreateOptionConditionException;
import com.myapi.demo.exception.NotSatisfiedCreateOptionGroupConditionException;
import com.myapi.demo.exception.NotSatisfiedDeleteOptionConditionException;
import com.myapi.demo.exception.NotSatisfiedDeleteOptionGroupConditionException;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {
	
	private ErrorResponse makeErrorResponse(Exception e, ErrorCode errorCode) {
		ErrorResponse errorResponse = ErrorResponse.builder()
		.code(errorCode.getCode())
		.message(e.getMessage() != null ? e.getMessage() : errorCode.getMessage())
		.build();
		return errorResponse;
	}
	
	@ExceptionHandler(BindingException.class)
	public ResponseEntity<ErrorResponse> handleBindingException(BindingException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.BINDING_EXCEPTION);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(NotFoundSubCategoryException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundSubCategoryException(NotFoundSubCategoryException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.NOT_FOUND_SUB_CATEGORY_EXCEPTION);
				
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(NotFoundStoreException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundStoreException(NotFoundStoreException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.NOT_FOUND_STORE_EXCEPTION);
				
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(NotSatisfiedCreateOptionConditionException.class)
	public ResponseEntity<ErrorResponse> handleNotSatisfiedCreateOptionConditionException(NotSatisfiedCreateOptionConditionException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.NOT_SATISFIED_CREATE_OPTION_CONDITION_EXCEPTION);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(NotFoundOptionGroupException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundOptionGroupException(NotFoundOptionGroupException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.NOT_FOUND_OPTION_GROUP_EXCEPTION);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(NotSatisfiedDeleteOptionGroupConditionException.class)
	public ResponseEntity<ErrorResponse> handleNotSatisfiedDeleteOptionConditionException(NotSatisfiedDeleteOptionGroupConditionException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.NOT_SATISFIED_DELETE_OPTION_GROUP_CONDITION_EXCEPTION);
	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(NotSatisfiedDeleteOptionConditionException.class)
	public ResponseEntity<ErrorResponse> handleNotSatisfiedDeleteOptionConditionException(NotSatisfiedDeleteOptionConditionException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.NOT_SATISFIED_DELETE_OPTION_CONDITION_EXCEPTION);
	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(NotFoundOptionException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundOptionException(NotFoundOptionException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.NOT_FOUND_OPTION_EXCEPTION);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(NotSatisfiedCreateOptionGroupConditionException.class)
	public ResponseEntity<ErrorResponse> handleNotSatisfiedCreateOptionGroupConditionException(NotSatisfiedCreateOptionGroupConditionException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.NOT_SATISFIED_CREATE_OPTION_GROUP_CONDITION_EXCEPTION);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
	
	@ExceptionHandler(NotFoundUserException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundUserException(NotFoundUserException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.NOT_FOUND_USER_EXCEPTION);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundUserException(BadCredentialsException e){
		ErrorResponse errorResponse = makeErrorResponse(e, ErrorCode.BAD_CREDENTIAL_EXCEPTION);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
}
