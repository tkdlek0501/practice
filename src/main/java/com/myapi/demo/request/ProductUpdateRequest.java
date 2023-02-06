package com.myapi.demo.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.myapi.demo.domain.PriceControlType;
import com.myapi.demo.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
	
	@NotNull(message = "수정할 상품의 id를 입력해주세요.")
	private Long id;
	
	@NotBlank(message = "상품 이름을 입력해주세요.")
	private String name;
	
	@NotBlank(message = "상품 가격을 입력해주세요.")
	private int price;
	
	@NotBlank(message = "상품의 품절 여부를 입력해주세요.")
	private Boolean isSoldOut; // 품절시 노출 x
	
	@NotBlank(message = "상품의 관리 주체를 입력해주세요.")
	private PriceControlType priceControlType;
	
	public Product toEntity(ProductUpdateRequest request) {
		return Product.builder()
				.name(request.getName())
				.price(request.getPrice())
				.isSoldOut(request.getIsSoldOut())
				.priceControlType(request.getPriceControlType())
				.build();	
	}
}
