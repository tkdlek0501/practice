package com.myapi.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SubCategory {
	
	@Id @GeneratedValue
	@Column(name = "subcategory_id")
	private Long id;
	
	private String name;
}
