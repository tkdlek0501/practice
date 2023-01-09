package com.myapi.demo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.myapi.demo.domain.User;
import com.myapi.demo.domain.UserType;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateUserRequest {
	
	@NotBlank(message = "아이디를 입력해주세요.")
	private String username;
	
	@Pattern(regexp="[a-zA-Z1-9]{6,12}", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
	private String password;
	
	@NotBlank(message = "닉네임을 입력해주세요.")
	private String nickname;
	
	@NotBlank(message = "등급을 입력해주세요.")
	private UserType userType;
	
	public User toEntity(CreateUserRequest request) {
		return User.builder()
		.username(request.getUsername())
		.password(request.getPassword())
		.nickname(request.getNickname())
		.type(request.getUserType())
		.build();
	}
}
