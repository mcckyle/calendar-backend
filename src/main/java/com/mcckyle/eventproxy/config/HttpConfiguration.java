//***************************************************************************************
//
//   Filename: HttpConfiguration.java
//   Author: Kyle McColgan
//   Date: 3 October 2026
//   Description: This file contains a shared HTTP client class for Saint Louis Events.
//
//***************************************************************************************

package com.mcckyle.eventproxy.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class HttpConfiguration
{
    private static final Duration CACHE_TTL = Duration.ofMinutes(10);

    @Bean
    RestTemplate restTemplate()
    {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        requestFactory.setConnectTimeout(Duration.ofSeconds(5));
        requestFactory.setReadTimeout(Duration.ofSeconds(15));

        return new RestTemplate(requestFactory);
    }

    @Bean
    CacheManager cacheManager()
    {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("events");
        cacheManager.setCaffeine(Caffeine.newBuilder().maximumSize(250).expireAfterWrite(CACHE_TTL));
        return cacheManager;
    }
}
