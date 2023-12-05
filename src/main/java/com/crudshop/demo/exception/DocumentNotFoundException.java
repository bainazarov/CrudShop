package com.crudshop.demo.exception;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
