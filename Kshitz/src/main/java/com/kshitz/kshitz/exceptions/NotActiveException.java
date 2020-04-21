package com.kshitz.kshitz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotActiveException extends RuntimeException {
    public NotActiveException(String message){
        super(message);
    }

}
