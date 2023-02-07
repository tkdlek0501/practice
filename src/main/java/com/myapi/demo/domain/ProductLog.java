package com.myapi.demo.domain;

import javax.persistence.Entity;

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
	
	private PriceControlType orgPriceControlType;
	
	private PriceControlType updatePriceControlType;
	
}
