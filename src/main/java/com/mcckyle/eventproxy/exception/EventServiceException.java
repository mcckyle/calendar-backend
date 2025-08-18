package com.mcckyle.eventproxy.exception;

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