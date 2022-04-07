package com.example.chavepix.exceptions;

import com.example.chavepix.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ChavePixExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidDataException.class})
    public ResponseEntity<Object> handleInvalidDataException(InvalidDataException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<Object> handleDataNotFoundExcpetion(DataNotFoundException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
