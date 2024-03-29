package com.myapi.demo.dto;

import com.myapi.demo.domain.PriceControlType;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductSearchDto {
	private Long storeId;
	
	private String storeName;
	
	private String productName;
	
	private String code;
	
	private boolean isSoldOut;
	
	private PriceControlType priceControlType;

	@QueryProjection
	public ProductSearchDto(
			Long storeId,
			String storeName,
			String productName,
			String code,
			boolean isSoldOut,
			PriceControlType priceControlType
			) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.productName = productName;
		this.code = code;
		this.isSoldOut = isSoldOut;
		this.priceControlType = priceControlType;
	}
}
