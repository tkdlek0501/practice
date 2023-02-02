package com.myapi.demo.exception;

public class NotFoundProductException extends RuntimeException{
	
	public NotFoundProductException(String msg) {
		super(msg);
	}
}
