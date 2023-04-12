package com.myapi.demo.security;

import java.security.Key;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.myapi.demo.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
//	@Value("spring.jwt.secret")
    private static final String SECRET_KEY = "hj";
	
    // 30 min
    private final static Long expireTime = 30 * 60 * 1000L;
    
    private final Key key;
    
    public JwtTokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String createToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        return generateToken(userPrincipal);
    }


    private String generateToken(User userPrincipal) {
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

