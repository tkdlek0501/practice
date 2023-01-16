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
public class Option extends AuditEntity{
	
	private String name;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_group_id")
	private OptionGroup optionGroup;
	
	@OneToMany(mappedBy = "option")
	private List<OrderDetailOptionMap> orderDetailOptionMaps = new ArrayList<>();
	
	public void changeOptionGroup(OptionGroup optionGroup) {
		this.optionGroup = optionGroup;
		optionGroup.getOptions().add(this);
	}
	
	public void addOrderDetailOptionMap(OrderDetailOptionMap orderDetailOptionMap) {
		this.orderDetailOptionMaps.add(orderDetailOptionMap);
		orderDetailOptionMap.setOption(this);
	}
}
