package com.myapi.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Product extends AuditEntity{
	
	private String name;
	
	private int price;
	
	private String code;
	
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
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
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
		if(optionGroup.getProduct() != this) optionGroup.setProduct(this);
	}
	
	public void addOrderDetail(OrderDetail orderDetail) {
		this.orderDetails.add(orderDetail);
		orderDetail.setProduct(this);
	}
	
	
	public void update(Product product) {
		this.name = product.getName();
		this.price = product.getPrice();
		this.isSoldOut = product.isSoldOut();
		this.priceControlType = product.getPriceControlType();
	}
	
}
