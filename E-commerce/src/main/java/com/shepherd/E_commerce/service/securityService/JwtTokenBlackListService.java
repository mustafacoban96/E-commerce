package com.shepherd.E_commerce.service.securityService;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class JwtTokenBlackListService {

	private Set<String> blackList = new HashSet<>();
	
	public void blackListToken(String token) {
		blackList.add(token);
	}
	
	public boolean isTokenBlackListed(String token) {
		return blackList.contains(token);
	}
}
