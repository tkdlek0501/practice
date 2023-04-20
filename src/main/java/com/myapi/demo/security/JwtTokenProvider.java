package com.myapi.demo.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.myapi.demo.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider{
	
    private static final String SECRET_KEY = "hjrlaguswnsdlqslekrlsguswnsdlqslekrlaguswnsdlqslek";
	
    // 30 min
    private final static Long expireTime = 30 * 60 * 1000L;
    
//    private final PasswordEncoder passwordEncoder;
    
//    public JwtTokenProvider() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }
    
    public String createToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        return generateToken(userPrincipal);
    }


    private String generateToken(User userPrincipal) {
    	byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
    	
        Long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        Date expiryDate = new Date(nowMills + expireTime);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}

