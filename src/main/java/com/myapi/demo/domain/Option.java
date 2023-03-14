package com.myapi.demo.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.myapi.demo.request.OptionUpdateRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"optionGroup", "orderDetailOptionMaps"})
public class Option extends AuditEntity{
	
	private String name;
	
	private LocalDateTime expiredAt;
	
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
	
	public void expire() {
		this.expiredAt = LocalDateTime.now();
	}
	
}
