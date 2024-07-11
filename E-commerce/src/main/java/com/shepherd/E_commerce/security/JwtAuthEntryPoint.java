package com.shepherd.E_commerce.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.shepherd.E_commerce.models.Roles;
import com.shepherd.E_commerce.service.securityService.JwtService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint{
	private final JwtService jwtService;
	
	public JwtAuthEntryPoint(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		System.out.println("at entrypoint");
		
		
		//System.out.println("my request--------> " + request.getHeader("Authorization"));
		/*String access_token = request.getHeader("Authorization").substring(7);
		 if (!jwtService.isTokenExpired(access_token)) {
		        String refreshTokenUrl = "/auth/refreshtoken"; // Update with your refresh token endpoint
		        RequestDispatcher dispatcher = request.getRequestDispatcher(refreshTokenUrl);
		        dispatcher.forward(request, response);
		    } else {
		        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		    }*/
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		
		System.out.println("çıktı");
		
	}

}
