package com.myapi.demo.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductLog extends AuditEntity{
	
	private String orgName;
	
	private String updateName;
	
	private int orgPrice;
	
	private int updatePrice;
	
	private boolean orgIsSoldOut;
	
	private boolean updateIsSoldOut;
	
	@Enumerated(EnumType.STRING)
	private PriceControlType orgPriceControlType;
	
	@Enumerated(EnumType.STRING)
	private PriceControlType updatePriceControlType;
	
}
