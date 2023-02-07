package com.myapi.demo.event.dto;

import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;
import com.myapi.demo.domain.ProductLog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedProductEvent {

	private String orgName;
	
	private String updateName;
	
	private int orgPrice;
	
	private int updatePrice;
	
	private boolean orgIsSoldOut;
	
	private boolean updateIsSoldOut;
	
	private PriceControlType orgPriceControlType;
	
	private PriceControlType updatePriceControlType;
	
	public static UpdatedProductEvent toEvent(Product orgProduct, Product updateProduct) {
		
		return UpdatedProductEvent.builder()
			.orgName(orgProduct.getName())
			.updateName(updateProduct.getName())
			.orgPrice(orgProduct.getPrice())
			.updatePrice(updateProduct.getPrice())
			.orgPriceControlType(orgProduct.getPriceControlType())
			.updatePriceControlType(updateProduct.getPriceControlType())
			.build();
		
	}
	
	public static ProductLog toEntity(UpdatedProductEvent event) {
		
		return ProductLog.builder()
				.orgName(event.getOrgName())
				.updateName(event.getUpdateName())
				.orgPrice(event.getOrgPrice())
				.updatePrice(event.getUpdatePrice())
				.orgIsSoldOut(event.isOrgIsSoldOut())
				.updateIsSoldOut(event.isUpdateIsSoldOut())
				.orgPriceControlType(event.getOrgPriceControlType())
				.updatePriceControlType(event.getUpdatePriceControlType())
				.build();	
	}
	
	public UpdatedProductEvent(Product orgProduct, Product updateProduct) {
		this.orgName = orgProduct.getName();
		this.updateName = updateProduct.getName();
		this.orgPrice = orgProduct.getPrice();
		this.updatePrice = updateProduct.getPrice();
		this.orgIsSoldOut = orgProduct.isSoldOut();
		this.updateIsSoldOut = updateProduct.isSoldOut();
		this.orgPriceControlType = orgProduct.getPriceControlType();
		this.updatePriceControlType = updateProduct.getPriceControlType();
	}
	
}
