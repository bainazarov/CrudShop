package com.crudshop.demo.exception;

public class CopyingFileException extends RuntimeException {

    public CopyingFileException(String errorMessage) {
        super(errorMessage);
    }
}
