package com.myapi.demo.request;

import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ProductRequest {
	
	private String name;
	
	private int price;
	
	private String code;
	
	private int quantity;
	
	private Boolean isSoldOut;
	
	private PriceControlType priceControlType;
	
	public Product toEntity(ProductRequest request) {
		return Product.builder()
		.name(request.getName())
		.price(request.getPrice())
		.code(request.getCode())
		.quantity(request.getQuantity())
		.isSoldOut(request.getIsSoldOut())
		.priceControlType(request.getPriceControlType())
		.build();
	}
	
}
