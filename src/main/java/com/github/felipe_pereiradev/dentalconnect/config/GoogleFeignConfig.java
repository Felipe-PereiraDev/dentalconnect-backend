package com.github.felipe_pereiradev.dentalconnect.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleFeignConfig {

    @Value("${key.geocoding-api}")
    private String apiKey;

    @Bean
    public RequestInterceptor googleApiKeyInterceptor() {
        return template -> template.query("key", apiKey);
    }
}
