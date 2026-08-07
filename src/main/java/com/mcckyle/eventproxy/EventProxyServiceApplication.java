package com.mcckyle.eventproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EventProxyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventProxyServiceApplication.class, args);
	}

}
