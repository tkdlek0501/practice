package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MainCategory {
	
	@Id
	private Long id;
	
	private String name;
}