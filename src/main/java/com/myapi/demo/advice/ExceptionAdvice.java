package com.myapi.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionAdvice {
	
//	@ExceptionHandler(exException.class)
//	public ResponseEntity<> exException(Exception e){
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		// error 용 객체로 return 하기
		// ex.
		// {
		// code : ,
		// description :
		//}
//	}
}
