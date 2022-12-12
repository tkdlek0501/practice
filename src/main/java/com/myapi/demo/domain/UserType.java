package com.myapi.demo.domain;

import lombok.Getter;

@Getter
public enum UserType {
	
	ADMIN("관리자"), MAINMALL("메인몰"), SUBMALL("서브몰"), USER("사용자");
	
	private final String name;
	
	private UserType(String name) {
		this.name = name;
	}
}
