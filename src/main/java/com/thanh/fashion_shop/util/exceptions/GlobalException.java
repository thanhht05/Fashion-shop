package com.thanh.fashion_shop.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.thanh.fashion_shop.domain.respone.RestRespone;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {
            CommonException.class
    })
    public ResponseEntity<RestRespone<Object>> handleException(Exception ex) {
        RestRespone<Object> res = new RestRespone<>();
        res.setError(ex.getMessage());
        res.setMessage("Exception occurs");
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

    }
}
