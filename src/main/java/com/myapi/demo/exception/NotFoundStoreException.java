package com.myapi.demo.exception;

public class NotFoundStoreException extends RuntimeException{
	
	public NotFoundStoreException(String msg) {
		super(msg);
	}
	
}
