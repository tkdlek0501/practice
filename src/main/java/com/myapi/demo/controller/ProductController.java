package com.myapi.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapi.demo.domain.User;
import com.myapi.demo.dto.ProductSearchCondition;
import com.myapi.demo.dto.ProductSearchDto;
import com.myapi.demo.request.ProductCreateRequest;
import com.myapi.demo.request.ProductUpdateRequest;
import com.myapi.demo.response.MainMallProductResponse;
import com.myapi.demo.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1.0/product")
@Slf4j
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping("") // 등록시 옵션 등록도 같이 (최소 1개의 옵션그룹과 옵션을 가져야 한다)
	public ResponseEntity<Void> create(@Valid @RequestBody ProductCreateRequest request) {
		
		productService.create(request);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ProductSearchDto>> search(ProductSearchCondition request){
		
		return ResponseEntity.ok(productService.search(request));
	}
	
	@PutMapping("") // 상품 수정과 옵션 수정은 별개
	public ResponseEntity<Void> update(@Valid @RequestBody ProductUpdateRequest request){
		
		productService.update(request);
		
		return ResponseEntity.ok(null);
	}
	
	// 메인몰에 있는 상품 중 서브몰에 등록되지 않은 상품 (상품코드로 필터링)
	@GetMapping("/main-mall")
	public ResponseEntity<List<MainMallProductResponse>> findMainMallProduct(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		
		return ResponseEntity.ok(productService.findMainMallProduct(user));
	}
}
