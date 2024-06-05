package com.shepherd.E_commerce.exceptions;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;


@Component
@Data
public class ErrorObject {
	
	
	
	private Integer statusCode;
	private String message;
	private Date timestamp;
}
