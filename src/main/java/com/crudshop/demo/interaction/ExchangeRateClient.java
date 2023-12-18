package com.crudshop.demo.interaction;

import com.crudshop.demo.util.Currency;
import org.springframework.stereotype.Component;

@Component
public interface ExchangeRateClient {
    Double getExchangeRate(Currency currency);
}