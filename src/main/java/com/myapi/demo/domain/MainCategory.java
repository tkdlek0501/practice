package com.myapi.demo.domain;

import java.util.ArrayList;
import java.util.List;

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

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainCategory extends AuditEntity{
	
	private String name;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;
	
	@Builder.Default
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
