package com.mcckyle.eventproxy.service;

import com.mcckyle.eventproxy.exception.EventServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class EventService
{
    @Value("${ticketmaster.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchEvents(String city, String start, String end)
    {
        String ticketmasterUrl = String.format(
                "https://app.ticketmaster.com/discovery/v2/events.json?apikey=%s&city=%s&startDateTime=%sT00:00:00Z&endDateTime=%sT23:59:59Z",
                apiKey, city, start, end
        );

        try
        {
            return restTemplate.getForObject(ticketmasterUrl, String.class);
        }
        catch(HttpClientErrorException e)
        {
            throw new EventServiceException("Ticketmaster API returned client error: " + e.getStatusCode(), e);
        }
        catch(HttpServerErrorException e)
        {
            throw new EventServiceException("Ticketmaster API returned server error: " + e.getStatusCode(), e);
        }
        catch(ResourceAccessException e)
        {
            throw new EventServiceException("Unable to connect to Ticketmaster API. Please try again later. ", e);
        }
        catch(Exception e)
        {
            throw new EventServiceException("An unexpected error occurred while fetching events.", e);
        }
    }
}
