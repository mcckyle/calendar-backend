//***************************************************************************************
//
//   Filename: GlobalExceptionHandler.java
//   Author: Kyle McColgan
//   Date: 14 November 2026
//   Description: This file contains custom exception function definitions for Saint Louis Events.
//
//***************************************************************************************

package com.mcckyle.eventproxy.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EventServiceException.class)
    public ResponseEntity<Map<String, Object>> handleEventServiceException(EventServiceException ex)
    {
        return ResponseEntity
                .status(ex.getStatus())
                .body(createErrorBody(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex)
    {
        LOGGER.error("Unexpected error while processing an API request.", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorBody("An unexpected server error occurred."));
    }

    private Map<String, Object> createErrorBody(String message)
    {
        return Map.of("timestamp", Instant.now(), "error", message);
    }
}