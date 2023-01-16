package com.myapi.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "orders")
public class Order extends AuditEntity{

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
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
	public void changeStore(Store store) {
		this.store = store;
		store.getOrders().add(this);
	}
	
	public void changeUser(User user) {
		this.user = user;
		user.getOrders().add(this);
	}
	
	public void addOrderDetail(OrderDetail orderDetail) {
		this.orderDetails.add(orderDetail);
		orderDetail.setOrder(this);
	}
	
	public void changePayment(Payment payment) {
		this.payment = payment;
		payment.setOrder(this);
		
	}
}
