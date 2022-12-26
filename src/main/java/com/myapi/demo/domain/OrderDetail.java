package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Setter;

@Entity
public class OrderDetail {
	
	@Id
	private Long id;
	
	private String name;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	public void changeOrder(Order order) {
		this.order = order;
		order.getOrderDetails().add(this);
	}
	
	public void changeProduct(Product product) {
		this.product = product;
		product.getOrderDetails().add(this);
	}
}
