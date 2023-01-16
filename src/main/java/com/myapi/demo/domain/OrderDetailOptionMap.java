package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class OrderDetailOptionMap extends AuditEntity{
	
	private String name;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_detail_id")
	private OrderDetail orderDetail;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_id")
	private Option option;
	
	public void changeOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
		orderDetail.getOrderDetailOptionMaps().add(this);
	}
	
	public void changeOption(Option option) {
		this.option = option;
		option.getOrderDetailOptionMaps().add(this);
	}
}
