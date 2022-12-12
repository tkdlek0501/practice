package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coupon {
	
	@Id
	private Long id;
	
	private String name;
}
