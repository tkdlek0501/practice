package com.myapi.demo.exception;

public class BindingException extends RuntimeException{

	public BindingException(String msg) {
		super("잘못 입력된 값 : " + msg);
	}
}
