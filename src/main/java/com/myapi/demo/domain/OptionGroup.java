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
public class OptionGroup extends AuditEntity{
	
	private String name;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	@OneToMany(mappedBy = "optionGroup")
	private List<Option> options = new ArrayList<>();
	
	public void changeProduct(Product product){
		this.product = product;
		product.getOptionGroups().add(this);
	}
	
	public void addOption(Option option) {
		this.options.add(option);
		option.setOptionGroup(this);
	}
	
	
}
