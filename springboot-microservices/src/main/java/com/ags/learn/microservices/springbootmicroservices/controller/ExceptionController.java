package com.ags.learn.microservices.springbootmicroservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ags.learn.microservices.springbootmicroservices.util.CustomErrorResponse;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<CustomErrorResponse> handleException(Exception exception){
        CustomErrorResponse customErrorResponse = new CustomErrorResponse();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        customErrorResponse.setStatus(status.value());
        customErrorResponse.setTitle(status.name());
        customErrorResponse.setDescription(exception.getMessage());
        return new ResponseEntity<>(customErrorResponse, status);
    }
}
