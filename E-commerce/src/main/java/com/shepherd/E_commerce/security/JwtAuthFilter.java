package com.shepherd.E_commerce.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shepherd.E_commerce.models.User;
import com.shepherd.E_commerce.service.UserService;
import com.shepherd.E_commerce.service.securityService.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	private UserService userService;
	
	
	public JwtAuthFilter(JwtService jwtService,UserService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			String email= jwtService.extractEmail(token);
			
			
			if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				User user = (User) userService.loadUserByUsername(email);
				log.info("token validate " + email);
				log.info("access_token: " + token);
				System.out.println(jwtService.validateToken(token, user));
				
				if(jwtService.validateToken(token, user)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					System.out.println("principal:" + authToken.getPrincipal());
					log.info("principal: " + authToken.getPrincipal());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
