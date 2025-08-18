package com.mcckyle.eventproxy.controller;

import com.mcckyle.eventproxy.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventProxyController
{
    private final EventService eventService;

    public EventProxyController(EventService eventService)
    {
        this.eventService = eventService;
    }

    @GetMapping("/api/events")
    public String getEvents(@RequestParam String keyword)
    {
        return eventService.fetchEvents(keyword);
    }
}
