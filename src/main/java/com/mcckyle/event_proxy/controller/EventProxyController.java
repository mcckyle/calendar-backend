package com.mcckyle.event_proxy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class EventProxyController
{
    @Value("${ticketmaster.api.key}")
    private String apiKey;

    @GetMapping("/api/events")
    public String getEvents(@RequestParam String keyword)
    {
        String ticketmasterUrl = "https://app.ticketmaster.com/discovery/v2/events.json?apikey="
                                 + apiKey + "&keyword=" + keyword;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(ticketmasterUrl, String.class);

    }

}
