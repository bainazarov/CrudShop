package com.crudshop.demo.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}