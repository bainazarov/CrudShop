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

    private final Double DEFAULT_COURSE_RUB = 1.00;
    private final ExchangeRateClient exchangeRateClient;


    public Double getExchangeRate(Currency currency) {
        return Optional.ofNullable(getExchangeRateFromService(currency))
                .orElseGet(() -> getExchangeRateFromFile(currency));
    }

    private Double getExchangeRateFromFile(Currency currency) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final File file = new File("src/main/resources/exchange-rate.json");
            final ExchangeRate exchangeRate = objectMapper.readValue(file, ExchangeRate.class);
            log.info("Получаем курс из локального файла");

            return getExchangeRateByCurrency(exchangeRate, currency);
        } catch (IOException e) {

            log.error("Ошибка при чтении локального файла ");
            return DEFAULT_COURSE_RUB;
        }
    }

    private @Nullable Double getExchangeRateFromService(Currency currency) {
        log.info("Получаем курс из второго сервиса или из кэша");
        return exchangeRateClient.getExchangeRate(currency);
    }

    private Double getExchangeRateByCurrency(ExchangeRate exchangeRate, Currency currency) {
        return switch (currency) {
            case USD -> exchangeRate.getExchangeRateUSD();
            case RUB -> exchangeRate.getExchangeRateRUB();
            case EUR -> exchangeRate.getExchangeRateEUR();
        };
    }
}