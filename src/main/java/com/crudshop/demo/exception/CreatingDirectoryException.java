package com.crudshop.demo.exception;

import java.io.IOException;

public class CreatingDirectoryException extends RuntimeException {
    public CreatingDirectoryException(String errorMessage) {
        super(errorMessage);
    }
}
