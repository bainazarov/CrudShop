package com.crudshop.demo.interaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(name = "interaction.rate.stub", havingValue = "true")
public class ExchangeRateClientImplStub implements ExchangeRateClient {
    @Override
    public Double getExchangeRate() {
        log.info("Значение получено из стаба");
        return 20.00;
    }
}