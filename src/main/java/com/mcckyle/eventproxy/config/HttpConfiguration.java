//***************************************************************************************
//
//   Filename: HttpConfiguration.java
//   Author: Kyle McColgan
//   Date: 6 August 2026
//   Description: This file contains a shared HTTP client class for Saint Louis Events.
//
//***************************************************************************************

package com.mcckyle.eventproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfiguration
{
    @Bean
    RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
