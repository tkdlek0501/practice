package com.myapi.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Member {
	
	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	private String username;
	
	private String password;
	
	private String name;
	
	private int age;
}
