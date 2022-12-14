package com.myapi.demo.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "users") // user는 예약어라 사용 불가
public class User {
	
	@Id @GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private UserType type;
	
	private String username;
	
	private String password;
	
	private String nickname;
	
	@Setter
	@OneToOne(mappedBy="user", fetch = FetchType.LAZY)
	private Store store;
	
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	
	public void changeStore(Store store) {
		this.store = store;
		store.setUser(this);
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
		order.setUser(this);
	}
}
