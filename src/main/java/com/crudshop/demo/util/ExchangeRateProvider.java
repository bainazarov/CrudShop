package com.crudshop.demo.util;

import com.crudshop.demo.interaction.ExchangeRateClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateProvider {
    private final Double DEFAULT_COURSE = 50.00;
    private final ExchangeRateClient exchangeRateClient;


    public Double getExchangeRate() {
        return Optional.ofNullable(getExchangeRateFromService())
                .orElseGet(this::getExchangeRateFromFile);
    }

    private Double getExchangeRateFromFile() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final File file = new File("src/main/resources/exchange-rate.json");
            final ExchangeRate exchangeRate = objectMapper.readValue(file, ExchangeRate.class);

            log.info("Получаем курс из локального файла");
            return exchangeRate.getExchangeRate();
        } catch (IOException e) {
            log.error("Ошибка при чтении локального файла ");

            return DEFAULT_COURSE;
        }
    }

    private @Nullable Double getExchangeRateFromService() {
        log.info("Получаем курс из второго сервиса или из кэша");

        return exchangeRateClient.getExchangeRate();
    }
}