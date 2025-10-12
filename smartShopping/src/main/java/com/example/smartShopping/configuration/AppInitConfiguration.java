package com.example.smartShopping.configuration;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppInitConfiguration {
    @Bean
    ApplicationRunner initApplicationRunner() {
        return args -> {
            System.out.println("Application has started successfully!");
        };
    }
}
