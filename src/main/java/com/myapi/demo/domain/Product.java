package com.myapi.demo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@ToString(exclude = {"store", "subCategory", "optionGroups", "orderDetails"})
public class Product extends AuditEntity{
	
	private String name;
	
	private int price;
	
	private String code;
	
	private boolean isSoldOut;
	
	@Enumerated(EnumType.STRING)
	private PriceControlType priceControlType; // 이름을 price로 했지만, 상품 수정 권한
	
	private LocalDateTime expiredAt;
	
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
	
	public void updateToMain(Product product) {
		this.name = product.getName();
		this.price = product.getPrice();
	}
}
