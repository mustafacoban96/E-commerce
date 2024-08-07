package com.shepherd.E_commerce.service.securityService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.shepherd.E_commerce.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class JwtService {
	
	@Value("${jwt.key}")
	private String SECRET;
	
	
	@Value("${jwt.expiration}")
	private long jwtExpiration;
	
	
	//Generate Token function
	public String generateToken(String email) {
		
		Map<String, Object> claims = new HashMap<>();
		
		return createToken(claims,email,jwtExpiration);
	}
	
	private String createToken(Map<String, Object> claims,String email,long jwtExpiration) {
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(getSignKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		log.info("Using secret key: {}", SECRET); 
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	public Boolean validateToken(String token, User user) {
		String email = extractEmail(token);
		Date expiration = extractExpiration(token);
		log.info("current-email:"  + user.getEmail());
		//getEmail!!!!!!!!!!!!!
		return user.getEmail().equals(email) && expiration.after(new Date());
	}
	
	
	private Date extractExpiration(String token) {
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getExpiration();
				
	}
	
	public String extractEmail(String token) {
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
	public boolean isTokenExpired(String token) {
	    try {
	        Claims claims = Jwts.parserBuilder()
	                .setSigningKey(getSignKey())
	                .setAllowedClockSkewSeconds(60) 
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	        return claims.getExpiration().before(new Date());
	    } catch (ExpiredJwtException e) {
	        return true;
	    }
	}

	
	
	
}
