package com.myapi.demo.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.myapi.demo.domain.Product;
import com.myapi.demo.domain.ProductLog;
import com.myapi.demo.event.dto.UpdatedProductEvent;
import com.myapi.demo.repository.ProductLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListner {
	
	private final ProductLogRepository productLogRepository;
	
	@Async
	@TransactionalEventListener
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handleUpdatedProductEvent(UpdatedProductEvent event) {
		// TODO: test시 원래값과 변경값이 제대로 들어오는지 확인
		// (조회 시점과 update 시점 확인 필요)
		
		log.info("UpdatedProductEvent : {}", event);
		ProductLog productLog = UpdatedProductEvent.toEntity(event);
		ProductLog createdProductLog = productLogRepository.save(productLog);
		log.info("createdProductLog : {}", createdProductLog);
	}
}
