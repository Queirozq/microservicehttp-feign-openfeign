package com.Queirozq.invetory.service.api.exception;

public class ProductCreationFailedException extends RuntimeException{
    public ProductCreationFailedException(String message) {
        super(message);
    }
}
