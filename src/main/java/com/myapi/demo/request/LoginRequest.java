package com.myapi.demo.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude="password")
public class LoginRequest {
		
    @NotNull
    private String username;
    
    @NotNull
    private String password;  
	
}
