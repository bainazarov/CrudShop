package com.crudshop.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleProductNotFoundException(ProductNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails(e.getClass().getSimpleName(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleDocumentNotFoundException(DocumentNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails(e.getClass().getSimpleName(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        String errorMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ErrorDetails errorDetails = new ErrorDetails(e.getClass().getSimpleName(), errorMessages, LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorDetails errorDetails = new ErrorDetails(e.getClass().getSimpleName(), "Некорректный формат ID", LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(ArticleAlreadyExistsException.class)
    protected ResponseEntity<ErrorDetails> handleArticleAlreadyExistsException(ArticleAlreadyExistsException e) {
        String errorMessage = Optional.ofNullable(e.getExistedProductId())
                .map(id -> e.getMessage() + " id + " + id)
                .orElseGet(e::getMessage);

        ErrorDetails errorDetails = new ErrorDetails(e.getClass().getSimpleName(), errorMessage, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(CreatingDirectoryException.class)
    protected ResponseEntity<ErrorDetails> handleCreatingDirectoryException(CreatingDirectoryException e) {
        ErrorDetails errorDetails = new ErrorDetails(e.getClass().getSimpleName(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(CopyingFileException.class)
    protected ResponseEntity<ErrorDetails> handleCopyingFileException(CopyingFileException e) {
        ErrorDetails errorDetails = new ErrorDetails(e.getClass().getSimpleName(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(GetContentAsByteException.class)
    protected ResponseEntity<ErrorDetails> handleGetContentAsByteException(GetContentAsByteException e) {
        ErrorDetails errorDetails = new ErrorDetails(e.getClass().getSimpleName(), e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}