package com.payment_service.demo.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new CustomGlobalException("NO_RECORD_FOUND", "No record found", HttpStatus.NOT_FOUND);
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}