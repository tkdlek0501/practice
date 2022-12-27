package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Setter;

@Entity
public class Payment {
	
	@Id
	private Long id;
	
	private String name;
	
	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	private Order order;
	
	public void changeOrder(Order order) {
		this.order = order;
		order.setPayment(this);
	}
}
