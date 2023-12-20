package com.crudshop.demo.interaction;

import com.crudshop.demo.util.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"exchangeRate"})
public class ExchangeRateClientImpl implements ExchangeRateClient {

    private final RestTemplate exchangeRateRestTemplate;

    @Cacheable(unless = "#result == null")
    @Override
    public ExchangeRate getExchangeRate() {
        try {
            log.info("Делаем вызов второго сервиса");

            return exchangeRateRestTemplate.getForObject("/exchange", ExchangeRate.class);
        } catch (Exception e) {

            log.error("Ошибка получения курса из второго сервиса: " + e.getMessage());
            return null;
        }
    }
}