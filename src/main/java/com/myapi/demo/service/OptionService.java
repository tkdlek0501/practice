package com.myapi.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.Option;
import com.myapi.demo.domain.OptionGroup;
import com.myapi.demo.domain.Product;
import com.myapi.demo.exception.NotFoundProductException;
import com.myapi.demo.repository.OptionGroupRepository;
import com.myapi.demo.repository.OptionRepository;
import com.myapi.demo.repository.ProductRepository;
import com.myapi.demo.request.OptionGroupRequest;
import com.myapi.demo.request.OptionGroupUpdateRequest;
import com.myapi.demo.request.OptionRequest;
import com.myapi.demo.request.OptionUpdateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OptionService {
	
	private final ProductRepository productRepository;
	
	private final OptionGroupRepository optionGroupRepository;
	
	private final OptionRepository optionRepository;
	
	@Transactional
	public void addOptionGroup(Long productId, OptionGroupRequest request) {
		
		Product product = productRepository.findByIdAndExpiredAtIsNull(productId).orElseThrow(() -> new NotFoundProductException("상품을 찾을 수 없습니다"));
		
		// optionGroup 생성
		OptionGroup optionGroup = OptionGroupRequest.toEntity(request);
		optionGroup.changeProduct(product);
		OptionGroup createdOptionGroup = optionGroupRepository.save(optionGroup);
		
		// option 생성
		if(request.getOptionRequests().size() < 1) throw new NotFoundProductException("");
			// TODO: throw new NotSatisfiedCreateOptionConditionException();
		List<Option> options = request.getOptionRequests().stream().map(OptionRequest::toEntity).collect(Collectors.toList());
		options.forEach(opt -> {
			opt.changeOptionGroup(createdOptionGroup);
			optionRepository.save(opt);
		});
		
	}
	
	@Transactional
	public void addOption(Long optionGroupId, OptionRequest request) {
		
		OptionGroup optionGroup = optionGroupRepository.findById(optionGroupId).get();
				// TODO: .orElseThrow(exceptionSupplier);
		
		// 옵션 1개 추가
		Option option = OptionRequest.toEntity(request);
		option.changeOptionGroup(optionGroup);
		optionRepository.save(option);
	}
	
	@Transactional
	public void deleteOptionGroup(Long optionGroupId) {
		OptionGroup optionGroup = optionGroupRepository.findByIdAndExpiredAtIsNull(optionGroupId).get();
		// TODO: .orElseThrow(exceptionSupplier);
		
		// 1개 이하이면 삭제 불가
		Product product = productRepository.findByIdAndExpiredAtIsNull(optionGroup.getProduct().getId()).get();
		// TODO: exception
		
//		if(product.getOptionGroups().stream().filter(og -> og.getExpiredAt() == null).collect(Collectors.toList()).size() <= 1) throw new Exception();
		// TODO: exception
		
		optionGroup.getOptions().forEach(o -> o.expire());
		optionGroup.expire();
	}
	
	@Transactional
	public void deleteOption(Long optionId) {
		Option option = optionRepository.findByIdAndExpiredAtIsNull(optionId).get();
		// TODO: .orElseThrow(exceptionSupplier);
		
		OptionGroup optionGroup = optionGroupRepository.findByIdAndExpiredAtIsNull(option.getOptionGroup().getId()).get();
		
//		if(optionGroup.getOptions().stream().filter(o -> o.getExpiredAt() == null).collect(Collectors.toList()).size() <= 1) throw new Exception();
		// TODO: exception
		
		option.expire();
	}
	
	@Transactional
	public void updateOptionGroup(Long optionGroupId, OptionGroupUpdateRequest request) {
		OptionGroup optionGroup = optionGroupRepository.findByIdAndExpiredAtIsNull(optionGroupId).get();
		
		optionGroup.update(request);
	}
	
	@Transactional
	public void updateOption(Long optionId, OptionUpdateRequest request) {
		Option option = optionRepository.findByIdAndExpiredAtIsNull(optionId).get();
		OptionGroup optionGroup = optionGroupRepository.findByIdAndExpiredAtIsNull(option.getOptionGroup().getId()).get();
		
		option.expire(); // 기존 옵션 expire
		
		Option updateOption = OptionUpdateRequest.toEntity(request);
		updateOption.changeOptionGroup(optionGroup);
		optionRepository.save(updateOption);
	}
}
