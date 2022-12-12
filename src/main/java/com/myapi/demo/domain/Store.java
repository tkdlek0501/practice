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

import lombok.Setter;

@Entity
public class Store {
	
	@Id
	private Long id;
	
	private String name;
	
	private String description;
	
	private String businessNo;
	
	@Setter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	// 셀프조인 참고
	// https://velog.io/@titu/Spring-JPA-Self-Join-%EC%85%80%ED%94%84%EC%A1%B0%EC%9D%B8
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Store mainStore;
	
	@OneToMany(mappedBy = "mainStore")
	private List<Store> subStores = new ArrayList<>();
	
	public void changeUser(User user) {
		this.user = user;
		user.setStore(this);
	}
}
