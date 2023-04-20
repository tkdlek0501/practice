package com.myapi.demo.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapi.demo.domain.User;
import com.myapi.demo.repository.UserRepository;
import com.myapi.demo.request.CreateUserRequest;
import com.myapi.demo.service.security.UserDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
	
	// TODO: 순환참조 문제... 뭐지?
//	private final PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;
	
	@Transactional
	public void join(CreateUserRequest request) {
		User user = request.toEntity(request);
		
		// 중복 검증
		validateDuplicateUser(user.getUsername());
		
		// 비밀번호 암호화
//		user.changePassword(passwordEncoder.encode(user.getPassword()));
		
		userRepository.save(user);
	}
	
	
	// 중복 검증 (아이디 중복 막음)
	private void validateDuplicateUser(String username) {
		
		log.info("중복 검증 실행 username : {}", username);
		
		Optional<User> user = userRepository.findOneByUsername(username);
		
		if(user.isPresent()) {
			throw new IllegalStateException("이미 사용하고 있는 아이디입니다.");
		}
	}
	
}
