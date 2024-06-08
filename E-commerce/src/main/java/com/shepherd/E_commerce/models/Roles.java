package com.shepherd.E_commerce.models;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority{
	
	ROLE_USER("USER"),
	ROLE_ADMIN("ADMIN");
	
	private String value;
	
	Roles(String value){
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return name();
	}

}
