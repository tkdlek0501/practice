package com.myapi.demo.dto;

import java.util.List;

import com.myapi.demo.domain.OptionGroup;
import com.myapi.demo.domain.OrderDetail;
import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;
import com.myapi.demo.domain.Store;
import com.myapi.demo.domain.SubCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TempProduct {
	
	private String name;
	
	private int price;
	
	private Boolean isSoldOut;
	
	private PriceControlType priceControlType;
	
	public static TempProduct toTempProduct(Product product) {
		return TempProduct.builder()
				.name(product.getName())
				.price(product.getPrice())
				.isSoldOut(product.isSoldOut())
				.priceControlType(product.getPriceControlType())
				.build();
	}
	
}
