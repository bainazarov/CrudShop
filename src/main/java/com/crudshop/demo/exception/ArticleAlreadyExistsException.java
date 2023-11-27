package com.crudshop.demo.exception;

public class ArticleAlreadyExistsException extends RuntimeException {
    public ArticleAlreadyExistsException (String errorMessage) {
        super(errorMessage);
    }
}
