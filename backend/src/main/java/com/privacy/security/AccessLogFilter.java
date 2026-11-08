package com.privacy.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.privacy.Model.Entity.Client;
import com.privacy.repository.AccessLogRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessLogFilter extends OncePerRequestFilter {

	@Autowired
	private AccessLogRepository accessLogRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		LocalDateTime requestTime = LocalDateTime.now();
		String endpoint = request.getRequestURI();
		String httpMethod = request.getMethod();
		String ipAddress = request.getRemoteAddr();
		
		filterChain.doFilter(request, response);
		
		int statusCode = response.getStatus();
		Integer userId = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal()))
		{
			Object principal = auth.getPrincipal();
			if(principal instanceof Client) {
				userId = ((Client)principal).getUserID();
			}
		}
		
		accessLogRepository.insertAccessLog(userId, endpoint, httpMethod, requestTime, ipAddress, statusCode);
		
	}

}
