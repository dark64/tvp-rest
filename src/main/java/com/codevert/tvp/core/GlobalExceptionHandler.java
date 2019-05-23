package com.codevert.tvp.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map> handleError(Throwable throwable) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", throwable.getMessage());
        map.put("code", HttpStatus.BAD_REQUEST);
        map.put("exception", throwable.getClass().getSimpleName());
        return ResponseEntity.ok(map);
    }
}
