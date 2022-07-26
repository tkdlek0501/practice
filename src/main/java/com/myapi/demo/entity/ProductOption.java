package com.myapi.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductOption {
	
	@Id @GeneratedValue
	@Column(name = "product_option_id")
	private Long id;
	
}
