package com.myapi.demo.exception;

import com.myapi.demo.advice.ErrorCode;

public class NotFoundSubCategoryException extends RuntimeException{
	
	public NotFoundSubCategoryException(String msg) {
		super(msg);
	}
	
}
