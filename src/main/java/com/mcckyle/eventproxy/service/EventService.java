//***************************************************************************************
//
//   Filename: EventService.java
//   Author: Kyle McColgan
//   Date: 6 August 2026
//   Description: This file provides API functionality for Saint Louis Events.
//
//***************************************************************************************

package com.mcckyle.eventproxy.service;

import org.springframework.stereotype.Service;

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
        return ticketmasterClient.fetchEvents(city, start, end);
    }
}
