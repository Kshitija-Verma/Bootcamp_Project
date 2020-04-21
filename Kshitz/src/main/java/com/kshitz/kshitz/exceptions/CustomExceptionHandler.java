package com.kshitz.kshitz.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(EntityNotFoundException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse("Error occured due to the following reason:", details);
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleConstraintException(ConstraintViolationException ce, WebRequest request){
        String details = ce.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse("error occured",details);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleConstraintException2(javax.validation.ConstraintViolationException ce, WebRequest request){
         String details = ce.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse("error occured",details);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleConstraintException3(DataIntegrityViolationException ce, WebRequest request){
        String details = ce.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse("error occured",details);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
