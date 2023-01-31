package com.myapi.demo.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.myapi.demo.dto.ProductSearchCondition;
import com.myapi.demo.dto.ProductSearchDto;
import com.myapi.demo.dto.QProductSearchDto;
import com.myapi.demo.repository.custom.ProductRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.myapi.demo.domain.QProduct.product;
import static com.myapi.demo.domain.QStore.store;


public class ProductRepositoryImpl implements ProductRepositoryCustom{

	private final JPAQueryFactory query;
	
	public ProductRepositoryImpl(EntityManager em) {
		this.query = new JPAQueryFactory(em);
	}

	@Override
	public List<ProductSearchDto> search(ProductSearchCondition condition) {
		
		return query
				.select(new QProductSearchDto(
					store.id.as("storeId"),
					store.name.as("storeName"),
					product.name.as("productName"),
					product.code,
					product.quantity,
					product.isSoldOut,
					product.priceControlType
				))
				.from(product)
				.leftJoin(product.store, store)
				.where(
						priceGoe(condition.getPriceGoe()),
						priceLoe(condition.getPriceLoe())
						)
				.fetch();
	}
	
	private BooleanExpression priceGoe(Integer priceGoe) {
		return priceGoe != null ? product.price.goe(priceGoe) : null;
	}
	
	private BooleanExpression priceLoe(Integer priceLoe) {
		return priceLoe != null ? product.price.loe(priceLoe) : null;
	}
	
	
	
}
