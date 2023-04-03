package com.myapi.demo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.myapi.demo.request.OptionGroupUpdateRequest;

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
@NoArgsConstructor
@ToString(exclude = {"product", "options"})
public class OptionGroup extends AuditEntity{
	
	private String name;
	
	private boolean isRequired;
	
	private LocalDateTime expiredAt;
	
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
	
	public void expire() {
		this.expiredAt = LocalDateTime.now();
	}
	
	public void update(OptionGroupUpdateRequest request) {
		this.name = request.getName();
		this.isRequired = request.getIsRequired();
	}
}
