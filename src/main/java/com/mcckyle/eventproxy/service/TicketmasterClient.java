//***************************************************************************************
//
//   Filename: TicketmasterClient.java
//   Author: Kyle McColgan
//   Date: 14 November 2026
//   Description: This file contains networking functionality for Saint Louis Events.
//
//***************************************************************************************

package com.mcckyle.eventproxy.service;

import com.mcckyle.eventproxy.exception.EventServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;

//***************************************************************************************

@Service
public class TicketmasterClient
{
    private static final ZoneId EVENT_TIME_ZONE = ZoneId.of("America/Chicago");
    private static final String EMPTY_EVENTS_RESPONSE = "{\"_embedded\":{\"events\":[]}}";

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public TicketmasterClient(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "events", key="#city.toLowerCase() + '_' + #start + '_' + #end")
    public String fetchEvents(String city, String start, String end)
    {
        try
        {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);

            String startDateTime = startDate
                    .atStartOfDay(EVENT_TIME_ZONE)
                    .toInstant()
                    .toString();

            String endDateTime = endDate
                    .plusDays(1)
                    .atStartOfDay(EVENT_TIME_ZONE)
                    .toInstant()
                    .minusSeconds(1)
                    .toString();

            URI ticketmasterUri = UriComponentsBuilder
                    .fromUriString("https://app.ticketmaster.com/discovery/v2/events.json")
                    .queryParam("apikey", apiKey)
                    .queryParam("city", city)
                    .queryParam("startDateTime", startDateTime)
                    .queryParam("endDateTime", endDateTime)
                    .build()
                    .encode()
                    .toUri();

            String response = restTemplate.getForObject(ticketmasterUri, String.class);

            return response != null ? response : EMPTY_EVENTS_RESPONSE;
        }
        catch (HttpClientErrorException ex)
        {
            HttpStatus status = ex.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS
                    ? HttpStatus.SERVICE_UNAVAILABLE
                    : HttpStatus.BAD_GATEWAY;
            throw new EventServiceException("Ticketmaster API returned an error: " + ex.getStatusCode(), status, ex);
        }
        catch (ResourceAccessException ex)
        {
            throw new EventServiceException("Unable to connect to Ticketmaster. Please try again later.", HttpStatus.GATEWAY_TIMEOUT, ex);
        }
        catch (EventServiceException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new EventServiceException("Unexpected error while contacting Ticketmaster.", HttpStatus.BAD_GATEWAY, ex);
        }
    }
}
