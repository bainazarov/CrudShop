package com.crudshop.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class ExchangeRateProvider {
    private final double DEFAULT_COURSE = 50.00;

    public double getExchangeRate() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/main/resources/exchange-rate.json");
            ExchangeRate exchangeRate = objectMapper.readValue(file, ExchangeRate.class);
            return exchangeRate.getExchangeRate();
        } catch (IOException e) {
            log.error("Ошибка при чтении файла ");
            return DEFAULT_COURSE;
        }
    }
}