//***************************************************************************************
//
//   Filename: CorsConfig.java
//   Author: Kyle McColgan
//   Date: 30 January 2026
//   Description: This file provides CORS mappings for the Saint Louis Events Calendar.
//
//***************************************************************************************

package com.mcckyle.eventproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//***************************************************************************************

@Configuration
public class CorsConfig
{
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "https://mcckyle.github.io"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
