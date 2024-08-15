package com.shepherd.E_commerce.exceptions;

public class NoOrdersFoundException extends RuntimeException{

    public NoOrdersFoundException(String message) {
        super(message);
    }
}
