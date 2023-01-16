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
public class SubCategory extends AuditEntity{
	
	private String name;
	
	@OneToMany(mappedBy = "subCategory")
	private List<Product> products = new ArrayList<>();
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_category_id")
	private MainCategory mainCategory;

	public void ChangeMainCategory(MainCategory mainCategory) {
		this.mainCategory = mainCategory;
		mainCategory.getSubCategories().add(this);
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
		product.setSubCategory(this);
	}
}
