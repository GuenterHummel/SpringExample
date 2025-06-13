package com.gh.demoserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate astroRestTemplate(RestTemplateBuilder builder, @Value("${astro.baseUrl}") String baseUrl) {
        return builder.rootUri(baseUrl).build();
    }

    @Bean
    public RestTemplate anotherRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("https://other.html").build();
    }
}
