package com.mcckyle.event_proxy.exception;

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