package com.payment_service.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomGlobalException extends RuntimeException {

    private final String code;
    private final HttpStatus httpStatus;

    public CustomGlobalException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
