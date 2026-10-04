//***************************************************************************************
//
//   Filename: GlobalExceptionHandler.java
//   Author: Kyle McColgan
//   Date: 3 October 2026
//   Description: This file contains custom exception function definitions for Saint Louis Events.
//
//***************************************************************************************

package com.mcckyle.eventproxy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(EventServiceException.class)
    public ResponseEntity<Map<String, Object>> handleEventServiceException(EventServiceException ex)
    {
        Map<String, Object> body = Map.of("timestamp", LocalDateTime.now(), "error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex)
    {
        Map<String, Object> body = Map.of("timestamp", LocalDateTime.now(), "error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}