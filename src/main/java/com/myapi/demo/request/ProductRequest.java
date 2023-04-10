package com.myapi.demo.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.constraints.NotBlank;

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
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
	
	@NotBlank(message = "상품 이름을 입력해주세요.")
	private String name;
	
	@NotBlank(message = "상품 가격을 입력해주세요.")
	private int price;
	
	@NotBlank(message = "상품 코드가 입력되지 않았습니다.")
	private String code;

	private int quantity;
	
	@NotBlank(message = "상품의 품절 여부를 입력해주세요.")
	private Boolean isSoldOut; // 품절시 노출 x
	
	@NotBlank(message = "상품의 관리 주체를 입력해주세요.")
	private PriceControlType priceControlType; // 메인몰이면 서브몰에서 수정 불가
	
	@Builder.Default
	private List<OptionGroupRequest> optionGroupRequests = new ArrayList<>();
	
//	@NotNull // test시 주석
	private Long storeId;
	
//	@NotNull // test시 주석
	private Long subCategoryId;
	
	public Product toEntity(ProductRequest request, String owner) {
		return Product.builder()
		.name(request.getName())
		.price(request.getPrice())
		.code(owner + "-" + makeRandomString())
		.isSoldOut(request.getIsSoldOut())
		.priceControlType(request.getPriceControlType())
		.optionGroups(new ArrayList<>()) // product생성시 optionGroup도 생성 (persist) 하기 위해 미리
		.build();
	}
	
	public static String makeRandomString() {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	                                   .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	                                   .limit(targetStringLength)
	                                   .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	                                   .toString();
	    return generatedString;
	  }
	
}
