package com.crudshop.demo.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EmailAlreadyExistsException extends RuntimeException {
    private UUID existedEmailId;

    public EmailAlreadyExistsException(String errorMessage, UUID id) {
        super(errorMessage);
        this.existedEmailId = id;
    }

    public EmailAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}