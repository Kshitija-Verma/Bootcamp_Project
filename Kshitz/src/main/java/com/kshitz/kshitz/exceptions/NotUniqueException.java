package com.kshitz.kshitz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class NotUniqueException extends RuntimeException {
    public NotUniqueException(String message){
        super(message);
    }
}
