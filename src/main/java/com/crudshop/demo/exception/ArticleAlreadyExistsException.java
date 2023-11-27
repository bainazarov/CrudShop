package com.crudshop.demo.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ArticleAlreadyExistsException extends RuntimeException {
    private UUID existedProductId;

    public ArticleAlreadyExistsException(String errorMessage, UUID id) {
        super(errorMessage);
        this.existedProductId = id;
    }

    public ArticleAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
