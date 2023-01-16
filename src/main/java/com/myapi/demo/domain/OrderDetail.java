package com.myapi.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class OrderDetail extends AuditEntity{
	
	private String name;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	@OneToMany(mappedBy = "orderDetail")
	private List<OrderDetailOptionMap> orderDetailOptionMaps = new ArrayList<>();
	
	public void changeOrder(Order order) {
		this.order = order;
		order.getOrderDetails().add(this);
	}
	
	public void changeProduct(Product product) {
		this.product = product;
		product.getOrderDetails().add(this);
	}
	
	public void addOrderDetailOptionMap(OrderDetailOptionMap orderDetailOptionMap) {
		this.orderDetailOptionMaps.add(orderDetailOptionMap);
		orderDetailOptionMap.setOrderDetail(this);
	}
}
