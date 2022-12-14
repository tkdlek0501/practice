package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	private Long id;

	private String orderNo;
	
	private int totalPrice;
	
	private int totalDiscount;
	
	private int totalOptionPrice;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	public void changeStore(Store store) {
		this.store = store;
		store.getOrders().add(this);
	}
	
	public void changeUser(User user) {
		this.user = user;
		user.getOrders().add(this);
	}
}
