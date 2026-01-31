//***************************************************************************************
//
//   Filename: EventServiceException.java
//   Author: Kyle McColgan
//   Date: 30 January 2026
//   Description: This file contains custom exception prototypes for the Saint Louis Events Calendar.
//
//***************************************************************************************

package com.mcckyle.eventproxy.exception;

//***************************************************************************************

public class EventServiceException extends RuntimeException
{
    public EventServiceException(String message)
    {
        super(message);
    }

    public EventServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }
}