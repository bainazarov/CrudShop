package com.crudshop.demo.interaction;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@ConditionalOnProperty(name = "interaction.rate.stub", havingValue = "true")
public class ExchangeRateClientImplStub implements ExchangeRateClient {
    @Override
    public Double getExchangeRateClient() {
        return 100.00;
    }
}