package com.myapi.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id @GeneratedValue
	@Column(name = "order_id")
	private Long id;
	
	private int totalPrice;
	
	
}
