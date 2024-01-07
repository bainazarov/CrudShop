package com.crudshop.demo.currency;

import com.crudshop.demo.interaction.ExchangeRateClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateProvider {

    private final BigDecimal DEFAULT_COURSE_RUB = BigDecimal.valueOf(1.00);
    private final ExchangeRateClient exchangeRateClient;


    public BigDecimal getExchangeRate(Currency currency) {
        return Optional.ofNullable(getExchangeRateFromService(currency))
                .orElseGet(() -> getExchangeRateFromFile(currency));
    }

    private BigDecimal getExchangeRateFromFile(Currency currency) {
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

    private @Nullable BigDecimal getExchangeRateFromService(Currency currency) {
        log.info("Получаем курс из второго сервиса или из кэша");
        return Optional.ofNullable(exchangeRateClient.getExchangeRate())
                .map(rate -> getExchangeRateByCurrency(rate, currency)).orElse(null);
    }

    private BigDecimal getExchangeRateByCurrency(ExchangeRate exchangeRate, Currency currency) {
        return switch (currency) {
            case USD -> exchangeRate.getExchangeRateUSD();
            case RUB -> exchangeRate.getExchangeRateRUB();
            case EUR -> exchangeRate.getExchangeRateEUR();
        };
    }
}