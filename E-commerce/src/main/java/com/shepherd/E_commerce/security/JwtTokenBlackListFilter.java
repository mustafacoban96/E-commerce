package com.shepherd.E_commerce.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shepherd.E_commerce.service.securityService.JwtTokenBlackListService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenBlackListFilter extends OncePerRequestFilter{
	
	
	
	private final JwtTokenBlackListService jwtTokenBlackListService;
	
	
	

	public JwtTokenBlackListFilter(JwtTokenBlackListService jwtTokenBlackListService) {
		this.jwtTokenBlackListService = jwtTokenBlackListService;
	}




	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = extractTokenFromRequest(request);
		
		if(token != null && jwtTokenBlackListService.isTokenBlackListed(token)) {
			SecurityContextHolder.clearContext();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Inactive token");
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		 String bearerToken = request.getHeader("Authorization");
	        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	            return bearerToken.substring(7);
	        }
	        return null;
	    }
	
	
	
	
	
	

}
