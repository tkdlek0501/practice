package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Setter;

@Entity
public class Option {
	
	@Id
	private Long id;
	
	private String name;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_group_id")
	private OptionGroup optionGroup;
	
	public void changeOptionGroup(OptionGroup optionGroup) {
		this.optionGroup = optionGroup;
		optionGroup.getOptions().add(this);
	}
}
