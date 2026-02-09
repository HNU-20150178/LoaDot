package com.loadot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class LostArkConfig {

    @Value("${lostark.api.key}")
    private String apiKey;

    @Value("${lostark.api.base-url}")
    private String baseUrl;

    @Bean
    public WebClient lostarkWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", apiKey)
                .build();
    }
}