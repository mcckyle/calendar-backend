//***************************************************************************************
//
//   Filename: EventService.java
//   Author: Kyle McColgan
//   Date: 14 November 2026
//   Description: This file provides API functionality for Saint Louis Events.
//
//***************************************************************************************

package com.mcckyle.eventproxy.service;

import com.mcckyle.eventproxy.exception.EventServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//***************************************************************************************

@Service
public class EventService
{
    private final TicketmasterClient ticketmasterClient;

    public EventService(TicketmasterClient ticketmasterClient)
    {
        this.ticketmasterClient = ticketmasterClient;
    }

    public String fetchEvents(String city, String start, String end)
    {
        if (city == null || city.isBlank())
        {
            throw new EventServiceException("A city must be provided.", HttpStatus.BAD_REQUEST);
        }

        final LocalDate startDate;
        final LocalDate endDate;

        try
        {
            startDate = LocalDate.parse(start);
            endDate = LocalDate.parse(end);
        }
        catch (DateTimeParseException ex)
        {
            throw new EventServiceException("Dates must use the YYYY-MM-DD format.", HttpStatus.BAD_REQUEST, ex);
        }

        if (endDate.isBefore(startDate))
        {
            throw new EventServiceException("The end date must not precede the start date.", HttpStatus.BAD_REQUEST);
        }

        return ticketmasterClient.fetchEvents(city.trim(), startDate.toString(), endDate.toString());
    }
}
