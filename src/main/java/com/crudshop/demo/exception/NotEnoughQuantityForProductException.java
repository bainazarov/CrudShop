package com.crudshop.demo.exception;

public class NotEnoughQuantityForProductException extends RuntimeException {
    public NotEnoughQuantityForProductException(String errorMessage) {
        super(errorMessage);
    }
}