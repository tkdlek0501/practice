package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Payment extends AuditEntity{
	
	private String name;
	
	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	private Order order;
	
	public void changeOrder(Order order) {
		this.order = order;
		order.setPayment(this);
	}
}
