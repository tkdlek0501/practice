package com.myapi.demo.exception;

public class NotFoundUserException extends RuntimeException{
	
	public NotFoundUserException(String msg) {
		super(msg);
	}
	
}
