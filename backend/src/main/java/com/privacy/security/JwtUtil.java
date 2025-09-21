package com.privacy.security;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;
	public String generateToken(String username, List<String> roles)
	{
		return Jwts.builder()
				.setSubject(username)
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractUsername(String token) {
	    return Jwts.parserBuilder()
	               .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
	               .build()
	               .parseClaimsJws(token)
	               .getBody()
	               .getSubject();
	}
	
	public boolean validateToken(String token) {
	    try {
	        Jwts.parserBuilder()
	            .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
	            .build()
	            .parseClaimsJws(token);
	        return true;
	    } catch (JwtException | IllegalArgumentException e) {
	        return false;
	    }
	}


}
