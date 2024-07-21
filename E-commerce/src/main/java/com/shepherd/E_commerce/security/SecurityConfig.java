package com.shepherd.E_commerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shepherd.E_commerce.models.Roles;
import com.shepherd.E_commerce.service.UserService;
import com.shepherd.E_commerce.service.securityService.JwtTokenBlackListService;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	private final JwtAuthFilter jwtAuthFilter;
	private final UserService userService;
	private PasswordEncoder passwordEncoder;
	private final JwtAuthEntryPoint jwtAuthEntryPoint;
	private final JwtTokenBlackListService jwtTokenBlackListService;
	
	public SecurityConfig(
			JwtAuthFilter jwtAuthFilter,
			UserService userService,
			PasswordEncoder passwordEncoder,
			JwtAuthEntryPoint jwtAuthEntryPoint,
			JwtTokenBlackListService jwtTokenBlackListService) {
		
		this.jwtAuthFilter = jwtAuthFilter;
		this.jwtAuthEntryPoint = jwtAuthEntryPoint;
		this.passwordEncoder = passwordEncoder;
		this.userService= userService;
		this.jwtTokenBlackListService = jwtTokenBlackListService;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))
				.authorizeHttpRequests(x ->
				
								x
								.requestMatchers("/api/v1/auth/**","/api/v1/auth/register/**","/api/v1/auth/login/**","/api/v1/auth/refreshtoken/**").permitAll()
								.requestMatchers("/admin/**").hasRole(Roles.ROLE_ADMIN.getValue())
								.requestMatchers("/api/v1/products/**").hasAnyRole(Roles.ROLE_ADMIN.getValue(),Roles.ROLE_USER.getValue())
								.requestMatchers("/api/v1/users/**").hasAnyRole(Roles.ROLE_ADMIN.getValue(),Roles.ROLE_USER.getValue())
								.requestMatchers("/api/v1/cart/**").hasRole(Roles.ROLE_USER.getValue())
						)
				.formLogin(AbstractHttpConfigurer::disable)
				.sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JwtTokenBlackListFilter(jwtTokenBlackListService), JwtAuthFilter.class)
				.build();
	}
	
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	
	
	
	
	
	
	
	

}
