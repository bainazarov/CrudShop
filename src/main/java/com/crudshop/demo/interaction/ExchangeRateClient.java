package com.crudshop.demo.interaction;

import com.crudshop.demo.util.Currency;
import com.crudshop.demo.util.ExchangeRate;
import org.springframework.stereotype.Component;

@Component
public interface ExchangeRateClient {
    ExchangeRate getExchangeRate();
}