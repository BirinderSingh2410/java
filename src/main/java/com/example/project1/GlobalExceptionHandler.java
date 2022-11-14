package com.example.project1;

import com.example.project1.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> noData(Exception ex){
        ApiResponse a = new ApiResponse();
        a.setData(null);
        a.setMessage(ex.getLocalizedMessage());
        a.setSuccess(false);
        return new ResponseEntity<>(a, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
