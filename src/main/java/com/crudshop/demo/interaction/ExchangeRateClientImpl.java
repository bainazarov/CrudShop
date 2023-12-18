package com.crudshop.demo.interaction;

import com.crudshop.demo.util.Currency;
import com.crudshop.demo.util.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"exchangeRate"})
public class ExchangeRateClientImpl implements ExchangeRateClient {

    private final RestTemplate exchangeRateRestTemplate;

    @Cacheable(unless = "#result == null")
    @Override
    public Double getExchangeRate(Currency currency) {
        try {
            log.info("Делаем вызов второго сервиса");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Currency", currency.name());

            ExchangeRate exchangeRate = exchangeRateRestTemplate.exchange("/exchange", HttpMethod.GET,
                    new HttpEntity<>(httpHeaders), ExchangeRate.class).getBody();
            return Optional.ofNullable(exchangeRate).map(rate -> getExchangeRateByCurrency(rate, currency)).orElse(null);
        } catch (Exception e) {

            log.error("Ошибка получения курса из второго сервиса: " + e.getMessage());
            return null;
        }
    }

    private Double getExchangeRateByCurrency(ExchangeRate exchangeRate, Currency currency) {
        return switch (currency) {
            case USD -> exchangeRate.getExchangeRateUSD();
            case RUB -> exchangeRate.getExchangeRateRUB();
            case EUR -> exchangeRate.getExchangeRateEUR();
        };
    }
}