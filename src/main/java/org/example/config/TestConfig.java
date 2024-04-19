package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    @Value("${aws.region:us-west-2}") // Set default value to us-west-2 if property not found
    public String awsRegion() {
        return "us-west-2";
    }
}
