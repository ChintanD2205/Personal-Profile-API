package com.privacy.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Value("${jwt.secret}")
    private String secret;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getRequestURI();
        
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }
		
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			
		    String token = authHeader.substring(7);
		    
		    if (jwtUtil.validateToken(token)) {
		    	
		        String username = jwtUtil.extractUsername(token);
		        
		        Claims claims = Jwts.parserBuilder()
		        		.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
		        		.build()
		        		.parseClaimsJws(token)
		        		.getBody();
		        
		        List<String> roles = claims.get("roles", List.class);
		        
		        List<SimpleGrantedAuthority> authorities = roles.stream()
		        		.map(role -> new SimpleGrantedAuthority("ROLE_"+role))
		        		.toList();
		        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
		        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		    }

		}
		filterChain.doFilter(request, response);
	}

}
