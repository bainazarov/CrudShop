package com.crudshop.demo.interaction;

import com.crudshop.demo.currency.ExchangeRate;
import org.springframework.stereotype.Component;

@Component
public interface ExchangeRateClient {
    ExchangeRate getExchangeRate();
}