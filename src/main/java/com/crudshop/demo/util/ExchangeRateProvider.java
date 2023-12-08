package com.crudshop.demo.util;

import com.crudshop.demo.interaction.ExchangeRateClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"exchangeRate"})
public class ExchangeRateProvider {
    private final Double DEFAULT_COURSE = 50.00;
    private final ExchangeRateClient exchangeRateClient;


    @Cacheable
    public Double getExchangeRate() {
        try {
            return getExchangeRateFromService();
        } catch (Exception e) {

            log.error("Ошибка при получении курса из второго сервиса: " + e.getMessage());
            return getExchangeRateFromFile();
        }
    }

    private Double getExchangeRateFromFile() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final File file = new File("src/main/resources/exchange-rate.json");
            final ExchangeRate exchangeRate = objectMapper.readValue(file, ExchangeRate.class);

            log.info("Получили курс из локального файла");
            return exchangeRate.getExchangeRate();
        } catch (IOException e) {
            log.error("Ошибка при чтении локального файла ");

            return DEFAULT_COURSE;
        }
    }

    private Double getExchangeRateFromService() {
        log.info("Получили курс из второго сервиса ");

        return exchangeRateClient.getExchangeRateClient();
    }
}