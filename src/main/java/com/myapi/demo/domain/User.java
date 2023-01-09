package com.myapi.demo.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // user는 예약어라 사용 불가
public class User implements UserDetails{
	
	@Id @GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private UserType type;
	
	private String username;
	
	private String password;
	
	private String nickname;
	
	@Setter
	@OneToOne(mappedBy="user", fetch = FetchType.LAZY)
	private Store store;
	
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	
	public void changeStore(Store store) {
		this.store = store;
		store.setUser(this);
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
		order.setUser(this);
	}
	
	public void changePassword(String password) {
		this.password = password;
	}
	
	// 권한
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String type = String.valueOf(this.type);
		authorities.add(new SimpleGrantedAuthority("ROLE_" + type)); // hasrole에서 ROLE_ 로 판단
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
