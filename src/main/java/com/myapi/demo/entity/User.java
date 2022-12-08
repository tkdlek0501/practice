package com.myapi.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users") // user는 예약어라 사용 불가
public class User {
	
	@Id @GeneratedValue
	private Long id;
	
	private String name;
}
