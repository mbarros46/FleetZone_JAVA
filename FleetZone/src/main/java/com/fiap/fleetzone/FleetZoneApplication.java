package com.fiap.fleetzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FleetZoneApplication {
    public static void main(String[] args) {
        SpringApplication.run(FleetZoneApplication.class, args);
    }
}
