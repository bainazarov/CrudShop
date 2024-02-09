package com.crudshop.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Value("${addressservice.base.url}")
    private String addressBaseUrl;

    @Bean
    public RestTemplate exchangeRateRestTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(addressBaseUrl)
                .build();
    }
}
