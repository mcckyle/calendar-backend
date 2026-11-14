//***************************************************************************************
//
//   Filename: EventServiceException.java
//   Author: Kyle McColgan
//   Date: 14 November 2026
//   Description: This file contains custom exception prototypes for the Saint Louis Events Calendar.
//
//***************************************************************************************

package com.mcckyle.eventproxy.exception;

//***************************************************************************************

import org.springframework.http.HttpStatus;

public class EventServiceException extends RuntimeException
{
    private final HttpStatus status;

    public EventServiceException(String message)
    {
        this(message, HttpStatus.BAD_GATEWAY, null);
    }

    public EventServiceException(String message, Throwable cause)
    {
        this(message, HttpStatus.BAD_GATEWAY, cause);
    }

    public EventServiceException(String message, HttpStatus status)
    {
        this(message, status, null);
    }

    public EventServiceException(String message, HttpStatus status, Throwable cause)
    {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus()
    {
        return status;
    }
}