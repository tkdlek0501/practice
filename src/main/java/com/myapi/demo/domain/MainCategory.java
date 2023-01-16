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
public class MainCategory extends AuditEntity{
	
	private String name;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;
	
	@OneToMany(mappedBy = "mainCategory")
	private List<SubCategory> subCategories = new ArrayList<>();
	
	public void changeStore(Store store) {
		this.store = store;
		store.getMainCategories().add(this);
	}
	
	public void addSubCategory(SubCategory subCategory) {
		this.subCategories.add(subCategory);
		subCategory.setMainCategory(this);
	}
	
}
