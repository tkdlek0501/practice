package com.myapi.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductOption {
	
	@Id @GeneratedValue
	private Long id;
	
}
