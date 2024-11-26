package com.learn.demo.app_a_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DefaultExecptionHandler {


    @ExceptionHandler
    public ResponseEntity defaulthanlder(Exception ex){
        Map<String,String> err = new HashMap<>();
        err.put("error","not found");
        err.put("msg",ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
