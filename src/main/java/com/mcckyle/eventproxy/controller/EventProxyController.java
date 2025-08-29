package com.mcckyle.eventproxy.controller;

import com.mcckyle.eventproxy.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EventProxyController
{
    private final EventService eventService;

    public EventProxyController(EventService eventService)
    {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public String getEvents(@RequestParam String city, @RequestParam String start, @RequestParam String end)
    {
        return eventService.fetchEvents(city, start, end);
    }
}
