package com.crudshop.demo.interaction;

import com.crudshop.demo.util.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"exchangeRate"})
public class ExchangeRateClient {

    private final RestTemplate exchangeRateRestTemplate;

    @Cacheable(unless = "#result == null")
    public @Nullable Double getExchangeRate() {
        try {
            log.info("Делаем вызов второго сервиса");
            ExchangeRate exchangeRate = exchangeRateRestTemplate.getForObject("/exchange", ExchangeRate.class);

            return Optional.ofNullable(exchangeRate).map(ExchangeRate::getExchangeRate).orElse(null);
        } catch (Exception e) {
            log.error("Ошибка получения курса из второго курса" + e.getMessage());
            return null;
        }
    }
}