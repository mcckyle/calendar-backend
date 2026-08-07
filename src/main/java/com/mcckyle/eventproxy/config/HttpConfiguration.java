//***************************************************************************************
//
//   Filename: HttpConfiguration.java
//   Author: Kyle McColgan
//   Date: 6 August 2026
//   Description: This file contains a shared HTTP client class for Saint Louis Events.
//
//***************************************************************************************

package com.mcckyle.eventproxy.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class HttpConfiguration
{
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CacheManager cacheManager()
    {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("events");
        cacheManager.setCaffeine(Caffeine.newBuilder().maximumSize(250).expireAfterWrite(Duration.ofMinutes(10)));
        return cacheManager;
    }
}
