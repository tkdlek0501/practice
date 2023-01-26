package com.myapi.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString(exclude = {"mainStore", "subStores", "user", "products", "orders", "mainCategories"})
public class Store extends AuditEntity{
	
	private String name;
	
	private String description;
	
	private String businessNo;
	
	// 셀프조인 참고
	// https://velog.io/@titu/Spring-JPA-Self-Join-%EC%85%80%ED%94%84%EC%A1%B0%EC%9D%B8
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Store mainStore;
	
	@Builder.Default
	@OneToMany(mappedBy = "mainStore")
	private List<Store> subStores = new ArrayList<>();
	
	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Builder.Default
	@OneToMany(mappedBy = "store")
	private List<Product> products = new ArrayList<>();
	
	@Builder.Default
	@OneToMany(mappedBy = "store")
	private List<Order> orders = new ArrayList<>();
	
	@Builder.Default
	@OneToMany(mappedBy = "store")
	private List<MainCategory> mainCategories = new ArrayList<>();
	
	public void changeUser(User user) {
		this.user = user;
		user.setStore(this);
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
		product.setStore(this);
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
		order.setStore(this);
	}
	
	public void addMainCategory(MainCategory mainCategory) {
		this.mainCategories.add(mainCategory);
		mainCategory.setStore(this);
	}
}
