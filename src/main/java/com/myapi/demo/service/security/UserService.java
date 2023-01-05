package com.myapi.demo.service.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myapi.demo.domain.User;
import com.myapi.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// TODO: orElseThrow로 변경하기
		User user = userRepository.findOneByUsername(username).orElse(null);
		
		if(user == null) {
			throw new UsernameNotFoundException("입력한 ID와 일치하는 유저가 조회되지 않습니다.");
		}
		
		return user;
	}
}
