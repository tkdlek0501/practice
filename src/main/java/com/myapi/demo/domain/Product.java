package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	public void changeStore(Store store) {
		this.store = store;
		store.getProducts().add(this);
	}
	
	public void changeSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
		subCategory.getProducts().add(this);
	}

	
}
