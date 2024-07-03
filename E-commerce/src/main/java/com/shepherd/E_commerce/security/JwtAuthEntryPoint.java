package com.shepherd.E_commerce.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.shepherd.E_commerce.models.Roles;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		//System.out.println("asdasd " + request.getRemoteUser());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		
	}

}
