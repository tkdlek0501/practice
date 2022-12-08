package com.myapi.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Refund {
	
	@Id
	private Long id;
	
	private String name;
}
