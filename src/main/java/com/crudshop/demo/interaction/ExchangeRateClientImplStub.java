package com.crudshop.demo.interaction;

import com.crudshop.demo.util.Currency;
import com.crudshop.demo.util.ExchangeRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(name = "interaction.rate.stub", havingValue = "true")
public class ExchangeRateClientImplStub implements ExchangeRateClient {
    @Override
    public ExchangeRate getExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setExchangeRateRUB(BigDecimal.valueOf(20.0));
        log.info("Значение получено из стаба");

        return exchangeRate;
    }
}