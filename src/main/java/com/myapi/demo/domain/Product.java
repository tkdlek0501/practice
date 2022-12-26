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
public class Product {
	
	@Id
	private Long id;
	
	private String name;
	
	private int price;
	
	private String code;
	
	private int quantity;
	
	private boolean isSoldOut;
	
	private PriceControlType priceControlType;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_category_id")
	private SubCategory subCategory;
	
	@OneToMany(mappedBy = "product")
	private List<OptionGroup> optionGroups = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	public void changeStore(Store store) {
		this.store = store;
		store.getProducts().add(this);
	}
	
	public void changeSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
		subCategory.getProducts().add(this);
	}

	public void addOptionGroup(OptionGroup optionGroup) {
		this.optionGroups.add(optionGroup);
		optionGroup.setProduct(this);
	}
	
	public void addOrderDetail(OrderDetail orderDetail) {
		this.orderDetails.add(orderDetail);
		orderDetail.setProduct(this);
	}
}
