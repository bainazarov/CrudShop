package com.crudshop.demo.interaction;

import com.crudshop.demo.util.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateClientImpl implements ExchangeRateClient {

    private final RestTemplate exchangeRateRestTemplate;

    @Override
    public Double getExchangeRateClient() {
        ExchangeRate exchangeRate = exchangeRateRestTemplate.getForObject("/exchange", ExchangeRate.class);
        return exchangeRate.getExchangeRate();
    }
}