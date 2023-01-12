package com.myapi.demo.service.security;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.User;
import com.myapi.demo.domain.UserType;
import com.myapi.demo.repository.UserRepository;
import com.myapi.demo.request.CreateUserRequest;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class UserServiceTest {
	
	@Autowired UserRepository userRepository;
	
	@Autowired PasswordEncoder passwordEncoder;
	
	// 회원가입
	@Test
	public void join() {
		// given
		CreateUserRequest request = CreateUserRequest.builder()
				.username("khj0501")
				.password("12345")
				.userType(UserType.ADMIN)
				.nickname("hj")
				.build();
		
		// when
		User user = request.toEntity(request);
		
		Optional<User> checkUser = userRepository.findOneByUsername(user.getUsername());
		if(checkUser.isPresent()) {
			throw new IllegalStateException("이미 사용하고 있는 아이디입니다.");
		}
		
		user.changePassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser = userRepository.save(user);
		
		// then
		User findUser = userRepository.findById(savedUser.getId()).orElse(null);
		log.info("생성된 user : {}", findUser);
		
		assertEquals(savedUser, findUser);
	}
	
}
