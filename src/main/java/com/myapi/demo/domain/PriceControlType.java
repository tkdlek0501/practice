package com.myapi.demo.domain;

public enum PriceControlType {
	
	MAINMALL("메인몰"), SUBMALL("서브몰");
	
	private final String name;
	
	private PriceControlType(String name) {
		this.name = name;
	}
	
	
}
