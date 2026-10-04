//***************************************************************************************
//
//   Filename: TicketmasterClient.java
//   Author: Kyle McColgan
//   Date: 3 October 2026
//   Description: This file contains networking functionality for Saint Louis Events.
//
//***************************************************************************************

package com.mcckyle.eventproxy.service;

import com.mcckyle.eventproxy.exception.EventServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

//***************************************************************************************

@Service
public class TicketmasterClient
{
    @Value("${ticketmaster.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public TicketmasterClient(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "events", key="#city + '_' + #start + '_' + #end")
    public String fetchEvents(String city, String start, String end)
    {
        URI ticketmasterUri = UriComponentsBuilder
                .fromUriString("https://app.ticketmaster.com/discovery/v2/events.json")
                .queryParam("apikey", apiKey)
                .queryParam("city", city)
                .queryParam("startDateTime", start + "T00:00:00Z")
                .queryParam("endDateTime", end + "T23:59:59Z")
                .build()
                .encode()
                .toUri();

        try
        {
            return restTemplate.getForObject(ticketmasterUri, String.class);
        }
        catch (HttpClientErrorException ex)
        {
            throw new EventServiceException("Ticketmaster API returned client error: " + ex.getStatusCode(), ex);
        }
        catch (HttpServerErrorException ex)
        {
            throw new EventServiceException("Ticketmaster API returned server error: " + ex.getStatusCode(), ex);
        }
        catch (ResourceAccessException ex)
        {
            throw new EventServiceException("Unable to connect to Ticketmaster API. " + "Please try again later.", ex);
        }
        catch (Exception ex)
        {
            throw new EventServiceException("Unexpected error while contacting Ticketmaster.", ex);
        }
    }
}
