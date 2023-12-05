package com.crudshop.demo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ConvertPriceToDollar {
    private final ExchangeRateProvider exchangeRateProvider;

    public double convertPriceToDollars(double price) throws IOException {
        double exchangeRate = exchangeRateProvider.getExchangeRate();
        return price / exchangeRate;
    }
}