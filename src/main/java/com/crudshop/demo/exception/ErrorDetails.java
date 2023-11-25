package com.crudshop.demo.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorDetails {

    private String nameException;
    private String message;
    private LocalDateTime time;
}
