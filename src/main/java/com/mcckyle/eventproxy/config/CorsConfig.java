//***************************************************************************************
//
//   Filename: CorsConfig.java
//   Author: Kyle McColgan
//   Date: 14 November 2026
//   Description: This file provides CORS mappings for the Saint Louis Events Calendar.
//
//***************************************************************************************

package com.mcckyle.eventproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//***************************************************************************************

@Configuration
public class CorsConfig
{
    private static final List<String> ALLOWED_ORIGINS = List.of(
            "http://localhost:5173",
            "https://mcckyle.github.io"
    );

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                ALLOWED_ORIGINS.toArray(String[]::new)
                        )
                        .allowedMethods("GET", "OPTIONS")
                        .allowedHeaders("*")
                        .maxAge(3600);
            }
        };
    }
}
