
package com.example.transportapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Arrays;

@SpringBootApplication
public class TransportApiApplication {
    public static void main(String[] args) {
        System.out.println(">>> TransportApiApplication starting...");
        ConfigurableApplicationContext context = SpringApplication.run(TransportApiApplication.class, args);
        System.out.println("Active profiles: " + Arrays.toString(context.getEnvironment().getActiveProfiles()));
    }
}
